package com.eventhub.payment_service.messaging;

import com.eventhub.payment_service.messaging.dto.PaymentProcessRequest;
import com.eventhub.payment_service.messaging.dto.PaymentProcessResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class PaymentRequestListener {

    private final KafkaTemplate<String, Object> kafka;
    private final String resultTopic;

    public PaymentRequestListener(KafkaTemplate<String, Object> kafka,
                                  @Value("${topics.payment.process.result}") String resultTopic) {
        this.kafka = kafka;
        this.resultTopic = resultTopic;
    }

    @KafkaListener(
            topics = "${topics.payment.process.request}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "paymentKafkaListenerContainerFactory"
    )
    public void onPaymentRequest(@Payload PaymentProcessRequest req) {
        // Basit mock: %80 PAID, %20 FAILED
        boolean paid = ThreadLocalRandom.current().nextInt(10) < 8;

        var result = paid
                ? PaymentProcessResult.paid(req.orderId())
                : PaymentProcessResult.failed(req.orderId(), "mock-declined");

        kafka.send(resultTopic, req.orderId(), result);
        System.out.println("[payment] handled orderId=" + req.orderId() + " -> " + result.status());
    }
}
