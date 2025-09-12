package com.eventhub.order_service.messaging.dto;

public record PaymentProcessRequest(String orderId, double amount, String currency) {
    public static PaymentProcessRequest of(String orderId, double amount, String currency) {
        return new PaymentProcessRequest(orderId, amount, currency);
    }
}
