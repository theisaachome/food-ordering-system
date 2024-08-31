package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand){

        return  Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getOrderItems().stream().map(orderItem->
                    new Product(new ProductId(orderItem.getProductId()))).collect(Collectors.toList())).build();
    }
    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand){
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .price(new Money(createOrderCommand.getPrice()))
                .orderItems(orderItemsToOrderItemEntities(createOrderCommand.getOrderItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order,String message){
        return CreateOrderResponse.builder()
                .trackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(orderItem ->
                       OrderItem.builder()
                               .products(new Product(new ProductId(orderItem.getProductId())))
                               .quantity(orderItem.getQuantity())
                               .price(new Money(orderItem.getPrice()))
                               .subTotal(new Money(orderItem.getSubTotal()))
                               .build())
                .collect(Collectors.toList());
    }

    public StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress){
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order){
        return  TrackOrderResponse
                .builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessage(order.getFailureMessages())
                .build();
    }
}
