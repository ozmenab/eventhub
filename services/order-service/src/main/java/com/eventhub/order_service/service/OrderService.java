package com.eventhub.order_service.service;

import com.eventhub.order_service.dto.CreateOrderRequest;

import java.util.UUID;

public interface OrderService {
    UUID createOrder(CreateOrderRequest request);
    void markReserved(UUID orderId);
    void markRejected(UUID orderId);
}
