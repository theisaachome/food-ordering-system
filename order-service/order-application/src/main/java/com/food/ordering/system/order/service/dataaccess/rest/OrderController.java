package com.food.ordering.system.order.service.dataaccess.rest;

import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dataaccess.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.dataaccess.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.dataaccess.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "orders",produces = "application/vnd.api.v1+json")
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand){
        log.info("Create Order for customer {} at Restaurant {}", createOrderCommand.getCustomerId(), createOrderCommand.getRestaurantId());
        var response = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order created with Tracking {}",response.getTrackingId());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable UUID trackingId){
        var trackingResponse = orderApplicationService.trackOrder(TrackOrderQuery.builder().orderTrackingId(trackingId).build());
        log.info("Order status {} with tracking {}", trackingResponse.getOrderStatus(),trackingResponse.getOrderTrackingId());
        return ResponseEntity.ok(trackingResponse);
    }
}
