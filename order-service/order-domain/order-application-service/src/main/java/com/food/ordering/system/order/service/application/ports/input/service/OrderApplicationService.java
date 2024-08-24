package com.food.ordering.system.order.service.application.ports.input.service;

import com.food.ordering.system.order.service.application.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.application.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.application.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.application.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
