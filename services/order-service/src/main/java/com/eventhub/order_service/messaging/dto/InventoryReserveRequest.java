package com.eventhub.order_service.messaging.dto;

import java.util.UUID;

public record InventoryReserveRequest(
        String reservationId,
        String itemId,
        int qty
) {
    public static InventoryReserveRequest of(String itemId, int qty) {
        return new InventoryReserveRequest(UUID.randomUUID().toString(), itemId, qty);
    }
}