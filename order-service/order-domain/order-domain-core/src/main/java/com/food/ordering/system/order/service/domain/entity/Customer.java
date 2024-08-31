package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.entity.Aggregate;
import com.food.ordering.system.valueobject.CustomerId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Customer extends Aggregate<CustomerId> {
    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
