package com.food.ordering.system.order.service.dataaccess.ports.output.repository;

import com.food.ordering.system.order.service.dataaccess.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
