package com.eventhub.payment_service.messaging.dto;

public record PaymentProcessRequest(String orderId, double amount, String currency) {
}
