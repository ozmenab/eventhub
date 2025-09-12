package com.eventhub.order_service.messaging.dto;

public record InventoryReserveResult(String reservationId, String status, String reason) {
}
