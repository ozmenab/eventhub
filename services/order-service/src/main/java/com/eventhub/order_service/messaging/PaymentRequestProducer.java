package com.eventhub.order_service.messaging;

import com.eventhub.order_service.messaging.dto.PaymentProcessRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequestProducer {
    private final KafkaTemplate<String, Object> kafka;
    private final String topic;

    public PaymentRequestProducer(KafkaTemplate<String, Object> kafka,
                                  @Value("${topics.payment.process.request}") String topic) {
        this.kafka = kafka;
        this.topic = topic;
    }

    public void send(PaymentProcessRequest req) {
        kafka.send(topic, req.orderId(), req);
    }
}
