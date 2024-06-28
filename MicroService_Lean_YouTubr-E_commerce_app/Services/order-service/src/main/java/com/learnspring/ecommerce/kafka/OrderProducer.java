package com.learnspring.ecommerce.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    private final KafkaTemplate<String, OrderConformation> kafkaTemplate;

    public void sendOderConformation(OrderConformation orderConformation){
        log.info("Sending order conformation to {}", orderConformation);
        Message message = MessageBuilder
                .withPayload(orderConformation)
                .setHeader(KafkaHeaders.TOPIC,"order-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
