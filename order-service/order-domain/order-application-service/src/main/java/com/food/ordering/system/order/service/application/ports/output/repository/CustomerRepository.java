package com.food.ordering.system.order.service.application.ports.output.repository;

import com.food.ordering.system.order.service.application.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID id);
}
