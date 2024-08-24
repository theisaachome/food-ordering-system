package com.food.ordering.system.order.service.application;

import com.food.ordering.system.order.service.application.entity.Order;
import com.food.ordering.system.order.service.application.entity.Product;
import com.food.ordering.system.order.service.application.entity.Restaurant;
import com.food.ordering.system.order.service.application.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.application.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.application.event.OrderPaidEvent;
import com.food.ordering.system.order.service.application.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
    private final String UTC = "UTC";
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order,restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id {} has been initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }



    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id {} has been paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id {} has been approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage) {
        order.initCancel(failureMessage);
        log.info("Order payment is cancelling for order id {}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {
        order.cancel(failureMessage);
        log.info("Order with id {} has been cancelled", order.getId().getValue());

    }
    private void validateRestaurant(Restaurant restaurant) {
        if(!restaurant.isActive()){
            throw  new OrderDomainException("Restaurant with id "+restaurant.getId().getValue()+" is not currently active!");
        }
    }
    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getOrderItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct->{
            Product currentProduct = orderItem.getProducts();
            if(currentProduct.equals(restaurantProduct)){
                currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),restaurantProduct.getPrice());
            }
        }));
    }
}
