package com.food.ordering.system.order.service.application;

import com.food.ordering.system.order.service.application.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.application.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.application.entity.Order;
import com.food.ordering.system.order.service.application.exception.OrderNotFoundException;
import com.food.ordering.system.order.service.application.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.application.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.application.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper,
                                    OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> orderResult = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (orderResult.isEmpty()) {
            log.warn("Could not found Order with tracking id:{}", trackOrderQuery.getOrderTrackingId());
            throw  new OrderNotFoundException("Could not found Order with tracking id: "+trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
    }
}
