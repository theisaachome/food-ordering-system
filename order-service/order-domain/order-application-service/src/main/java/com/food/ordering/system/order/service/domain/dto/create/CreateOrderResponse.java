package com.food.ordering.system.order.service.domain.dto.create;

import com.food.ordering.system.valueobject.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class CreateOrderResponse {

    @NotNull
    private final UUID trackingId;
    @NotNull
    private final OrderStatus orderStatus;
    @NotNull
    private final String message;

}
