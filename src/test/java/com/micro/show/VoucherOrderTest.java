package com.micro.show;

import com.micro.show.mapper.VoucherOrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

import static com.micro.show.utils.RedisConstants.SECKILL_STOCK_KEY;

/**
 * @author muxiaoling
 * @date 2022/10/22 0:05
 */
@SpringBootTest
public class VoucherOrderTest {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private VoucherOrderMapper voucherOrderMapper;

    @Test
    void seckilStockLoad() {
        stringRedisTemplate.opsForValue().set(SECKILL_STOCK_KEY + 10, String.valueOf(100));
    }

    @Test
    void voucherOrderTest() {
        Set<Long> set = new HashSet<>();
        set.add(1L);
        voucherOrderMapper.getVoucherOrderCount(1L, 2L, set);
    }
}
