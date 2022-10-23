package com.micro.show.kafka;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableSet;
import com.micro.show.entity.VoucherOrder;
import com.micro.show.mapper.SeckillVoucherMapper;
import com.micro.show.mapper.VoucherOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

import static com.micro.show.utils.RedisConstants.SECKILL_ORDER_KEY;
import static com.micro.show.utils.RedisConstants.SECKILL_STOCK_KEY;

/**
 * @author muxiaoling
 * @date 2022/10/21 13:43
 */
@Component
@Slf4j
public class MQReceive {
    @Resource
    private VoucherOrderMapper voucherOrderMapper;
    @Resource
    private SeckillVoucherMapper seckillVoucherMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款
     * 根据订单状态，确定是否属于重复下单
     */
    private static final Set<Integer> voucherStatus = ImmutableSet.of(1, 2, 3, 5);


    @KafkaHandler
    @KafkaListener(topics = {"voucher-order-topic"},groupId = "voucher-order-group-1",
            errorHandler ="kafkaListenerErrorHandler", concurrency = "1")
    @Transactional(rollbackFor = Exception.class)
    public void receiveOrderMessage(String orderValue, Acknowledgment ack) {
        Long voucherId = null;
        Long userId = null;
        try {
            log.info("消费消息 {}", orderValue);
            if (StringUtils.isBlank(orderValue)) {
                throw new RuntimeException("消息为空");
            }
            VoucherOrder voucherOrder = JSONUtil.toBean(orderValue, VoucherOrder.class);
            if (voucherOrder == null) {
                throw new RuntimeException("订单处理出错");
            }
            voucherId = voucherOrder.getVoucherId();
            userId = voucherOrder.getUserId();
            if (voucherOrderMapper.getVoucherOrderCount(voucherOrder.getUserId(),
                    voucherOrder.getVoucherId(),
                    voucherStatus) > 0) {
                log.error("重复下单");
                throw new RuntimeException("不允许重复下单！");
            }
            voucherOrderMapper.insert(voucherOrder);
            seckillVoucherMapper.updateStock(voucherOrder.getVoucherId());
            log.info("消费消息成功, MySQL库存扣减成功");
        }catch (Exception e) {
            if (voucherId != null && userId != null) {
                log.info("补偿机制：增加库存");
                stringRedisTemplate.opsForValue().increment(SECKILL_STOCK_KEY + voucherId, 1L);
                log.info("补偿机制：从购买者列表中剔除");
                stringRedisTemplate.opsForSet().remove(SECKILL_ORDER_KEY + voucherId, String.valueOf(userId));
            }
            e.printStackTrace();
            throw new RuntimeException("发生错误");
        } finally {
            ack.acknowledge();
        }
    }
}
