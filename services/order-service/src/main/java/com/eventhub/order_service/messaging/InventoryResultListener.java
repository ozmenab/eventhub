package com.eventhub.order_service.messaging;

import com.eventhub.order_service.messaging.dto.InventoryReserveResult;
import com.eventhub.order_service.messaging.dto.PaymentProcessRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class InventoryResultListener {

    private final PaymentRequestProducer paymentProducer;

    public InventoryResultListener(PaymentRequestProducer paymentProducer) {
        this.paymentProducer = paymentProducer;
    }

    @KafkaListener(
            topics = "${topics.inventory.reserve.result}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "inventoryResultKafkaListenerContainerFactory"
    )
    public void onInventoryResult(@Payload InventoryReserveResult result) {
        System.out.println("[order] inventory result -> " + result.status()
                + " (reservationId=" + result.reservationId() + ")");

        if ("RESERVED".equalsIgnoreCase(result.status())) {
            var paymentReq = PaymentProcessRequest.of(result.reservationId(), 100.0, "TRY");
            paymentProducer.send(paymentReq);
            System.out.println("[order] payment.request sent for orderId=" + paymentReq.orderId());
        } else {
            System.out.println("[order] order rejected (no payment). reason=" + result.reason());
        }
    }
}
