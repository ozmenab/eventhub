package com.eventhub.order_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(UUID userId,
                                 BigDecimal totalAmount,
                                 String currency,
                                 String itemId,
                                 int qty) {
}
