package com.food.ordering.system.order.service.domain.order.mapper;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.order.entity.OrderAddressEntity;
import com.food.ordering.system.order.service.domain.order.entity.OrderEntity;
import com.food.ordering.system.order.service.domain.order.entity.OrderItemEntity;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.food.ordering.system.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order){
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .orderStatus(order.getOrderStatus())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemToOrderItemEntities(order.getOrderItems()))
                .failureMessage(order.getFailureMessages() !=null?
                        String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()):"")
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity->orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity){
     return Order.builder()
                .orderId(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .orderStatus(orderEntity.getOrderStatus())
                .price(new Money(orderEntity.getPrice()))
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity))
                .orderItems(orderItemEntityToOrderItem(orderEntity))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .failureMessages(orderEntity.getFailureMessage().isEmpty()?
                        new ArrayList<>(): new ArrayList<>(Arrays.asList(orderEntity.getFailureMessage().split(FAILURE_MESSAGE_DELIMITER))))
                .build();
    }

    private List<OrderItem> orderItemEntityToOrderItem(OrderEntity orderEntity) {
        return orderEntity.getItems()
                .stream()
                .map(item->
                    OrderItem.builder()
                            .orderItemId(new OrderItemId(item.getId()))
                            .products(new Product(new ProductId(item.getProductId())))
                            .price(new Money(item.getPrice()))
                            .quantity(item.getQuantity())
                            .subTotal(new Money(item.getSubTotal()))
                            .build()
                )
                .collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderEntity orderEntity) {
        return new  StreetAddress(orderEntity.getAddress().getId(),
                orderEntity.getAddress().getStreet(),
                orderEntity.getAddress().getPostCode(),
                orderEntity.getAddress().getCity());
    }

    private List<OrderItemEntity> orderItemToOrderItemEntities(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(data -> OrderItemEntity.builder()
                        .id(data.getId().getValue())
                        .productId(data.getProduct().getId().getValue())
                        .quantity(data.getQuantity())
                        .price(data.getPrice().getAmount())
                        .subTotal(data.getSubTotal().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        OrderAddressEntity orderAddressEntity = OrderAddressEntity.builder()
                .id(deliveryAddress.getStreetId())
                .city(deliveryAddress.getCity())
                .street(deliveryAddress.getStreet())
                .postCode(deliveryAddress.getPostalCode())
                .build();
        return orderAddressEntity;
    }
}
