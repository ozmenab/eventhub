package com.eventhub.order_service.messaging;

import com.eventhub.order_service.messaging.dto.PaymentProcessResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PaymentResultListener {

    @KafkaListener(
            topics = "${topics.payment.process.result}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "paymentResultKafkaListenerContainerFactory"
    )
    public void onPaymentResult(@Payload PaymentProcessResult result) {
        if ("PAID".equalsIgnoreCase(result.status())) {
            System.out.println("[order] payment OK -> order CONFIRMED (orderId=" + result.orderId() + ")");
        } else {
            System.out.println("[order] payment FAILED -> order CANCELLED (orderId="
                    + result.orderId() + ", reason=" + result.reason() + ")");
        }
    }
}
