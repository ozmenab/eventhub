package com.eventhub.inventory_service.messaging.dto;

public record InventoryReserveResult(String reservationId, String status, String reason) {
    public static InventoryReserveResult reserved(String reservationId) {
        return new InventoryReserveResult(reservationId, "RESERVED", null);
    }
    public static InventoryReserveResult rejected(String reservationId, String reason) {
        return new InventoryReserveResult(reservationId, "REJECTED", reason);
    }
}
