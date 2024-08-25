package com.food.ordering.system.order.service.dataaccess;

import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dataaccess.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.dataaccess.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.dataaccess.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@Slf4j
class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler,
                                       OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}

// remove public bcos we will share only Interface