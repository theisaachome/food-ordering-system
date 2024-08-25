package com.food.ordering.system.order.service.dataaccess.ports.input.service;

import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.dataaccess.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.dataaccess.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.service.dataaccess.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
