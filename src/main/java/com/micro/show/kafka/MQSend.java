package com.micro.show.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author muxiaoling
 * @date 2022/10/21 13:43
 */
@Component
@Slf4j
public class MQSend {
    private static final String TOPIC_VOUCHER_ORDER = "voucher-order-topic";

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendVoucherOrderMessage(String orderMsg) {
        log.info("接收消息成功:{}",orderMsg);
        kafkaTemplate.send(TOPIC_VOUCHER_ORDER, "key", orderMsg);
    }

}
