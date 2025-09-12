package com.eventhub.order_service.controller;

import com.eventhub.order_service.messaging.InventoryRequestProducer;
import com.eventhub.order_service.messaging.dto.InventoryReserveRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/test")
public class OrderTestController {

    private final InventoryRequestProducer producer;

    public OrderTestController(InventoryRequestProducer producer) {
        this.producer = producer;
    }


    @PostMapping("/reserve")
    public String reserve(@RequestParam String itemId, @RequestParam int qty) {
        var req = InventoryReserveRequest.of(itemId, qty);
        producer.send(req);
        return "sent reservationId=" + req.reservationId();
    }
}
