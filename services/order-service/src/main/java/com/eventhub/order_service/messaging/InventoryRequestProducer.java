package com.eventhub.order_service.messaging;

import com.eventhub.order_service.messaging.dto.InventoryReserveRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryRequestProducer {

    private final KafkaTemplate<String, Object> kafka;
    private final String topic;

    public InventoryRequestProducer(KafkaTemplate<String, Object> kafka,
                                    @Value("${topics.inventory.reserve.request}") String topic) {
        this.kafka = kafka;
        this.topic = topic;
    }

    public void send(InventoryReserveRequest req) {
        kafka.send(topic, req.reservationId(), req);
    }
}