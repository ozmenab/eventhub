package com.eventhub.payment_service.messaging.dto;


public record PaymentProcessResult(String orderId, String status, String providerRef, String reason) {
    public static PaymentProcessResult paid(String orderId) {
        return new PaymentProcessResult(orderId, "PAID", "mock-OK", null);
    }
    public static PaymentProcessResult failed(String orderId, String reason) {
        return new PaymentProcessResult(orderId, "FAILED", "mock-NOK", reason);
    }
}
