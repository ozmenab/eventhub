package com.eventhub.order_service.messaging.dto;

public record PaymentProcessResult(String orderId, String status, String providerRef, String reason) {
}
