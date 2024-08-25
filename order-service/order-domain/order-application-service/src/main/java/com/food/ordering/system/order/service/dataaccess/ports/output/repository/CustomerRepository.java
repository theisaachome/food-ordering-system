package com.food.ordering.system.order.service.dataaccess.ports.output.repository;

import com.food.ordering.system.order.service.dataaccess.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID id);
}
