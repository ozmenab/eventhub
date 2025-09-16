package com.eventhub.order_service.dto;

import com.eventhub.order_service.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderDto(UUID id,
                       UUID userId,
                       OrderStatus status,
                       BigDecimal totalAmount,
                       String currency,
                       Instant createdAt,
                       Instant updatedAt) {
}
