package com.eventhub.inventory_service.messaging;

import com.eventhub.inventory_service.messaging.dto.InventoryReserveRequest;
import com.eventhub.inventory_service.messaging.dto.InventoryReserveResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class InventoryListeners {

    private final KafkaTemplate<String, Object> kafka;
    private final String resultTopic;

    public InventoryListeners(KafkaTemplate<String, Object> kafka,
                              @Value("${topics.inventory.reserve.result}") String resultTopic) {
        this.kafka = kafka;
        this.resultTopic = resultTopic;
    }

    @KafkaListener(
            topics = "${topics.inventory.reserve.request}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReserveRequest(@Payload InventoryReserveRequest req) {
        // şimdilik basit kural: qty <= 5 ise onayla, >5 ise reddet
        var result = (req.qty() <= 5)
                ? InventoryReserveResult.reserved(req.reservationId())
                : InventoryReserveResult.rejected(req.reservationId(), "qty too high");

        kafka.send(resultTopic, req.reservationId(), result);
        System.out.println("[inventory] handled reservationId=" + req.reservationId() + " -> " + result.status());
    }
}