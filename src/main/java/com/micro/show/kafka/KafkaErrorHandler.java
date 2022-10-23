package com.micro.show.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author muxiaoling
 * @date 2022/10/21 18:06
 */
@Component("kafkaListenerErrorHandler")
@Slf4j
public class KafkaErrorHandler implements KafkaListenerErrorHandler {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_DLT=".DLT";

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        log.info("处理异常消息:{}", message.toString());
        //获取消息处理异常主题
        MessageHeaders headers = message.getHeaders();
        String topic=headers.get("kafka_receivedTopic") + TOPIC_DLT;
        //放入死讯队列
        kafkaTemplate.send(topic, message.getPayload());
        return message;
    }
}
