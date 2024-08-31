package com.food.ordering.system.order.service.domain.restaurant.exception;

public class RestaurantDataAccessException extends  RuntimeException{
    public RestaurantDataAccessException(String message) {
        super(message);
    }
}
