package com.eventhub.inventory_service.messaging.dto;

public record InventoryReserveRequest(String reservationId, String itemId, int qty) {
}
