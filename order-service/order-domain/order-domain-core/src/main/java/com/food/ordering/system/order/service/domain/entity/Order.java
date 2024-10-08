package com.food.ordering.system.order.service.domain.entity;
import com.food.ordering.system.entity.Aggregate;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.valueobject.*;
import java.util.List;
import java.util.UUID;

public class Order extends Aggregate<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> orderItems;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public static final String  FAILURE_MESSAGE_DELIMITER=",";

    public static Builder builder() {
        return new Builder();
    }

    public void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
    }

    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }
    public void pay(){
        if(orderStatus != OrderStatus.PENDING){
            throw new OrderDomainException("Order status is in Incorrect state for Pay Operation.");
        }
        orderStatus = OrderStatus.PAID;
    }
    public void approve(){
        if(orderStatus != OrderStatus.PAID){
            throw new OrderDomainException("Order status is in Incorrect state for Approve Operation.");
        }
        orderStatus = OrderStatus.APPROVED;
    }
    public void initCancel(List<String> failureMessages){
        if(orderStatus != OrderStatus.PAID){
            throw new OrderDomainException("Order status is in Incorrect state for InitCancel Operation.");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages){
        if(!(orderStatus == OrderStatus.CANCELLED || orderStatus == OrderStatus.PENDING)){
            throw new OrderDomainException("Order status is in Incorrect state for Cancel Operation.");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }


    private void updateFailureMessages(List<String> failureMessages) {
        if( this.failureMessages !=null&&failureMessages != null){
            this.failureMessages.addAll(failureMessages.stream().filter(String::isEmpty).toList());
        }
        if(this.failureMessages == null){
            this.failureMessages = failureMessages;
        }
    }


    private void validateItemsPrice() {
       Money orderItemTotal= orderItems.stream()
                .map((orderItem) -> {
                    validateItemPrice(orderItem);
                    return orderItem.getSubTotal();
                })
                .reduce(Money.ZERO,Money::add);
        System.out.println(orderItemTotal);
       if(!price.equals(orderItemTotal)){
           throw new OrderDomainException("Total Price : " + price.getAmount()
           + " is not equal to Order items total : " + orderItemTotal.getAmount() + "!");
       }
    }
    private void validateItemPrice(OrderItem orderItem) {
        if(!orderItem.isPriceValid()){
            throw new OrderDomainException("Order Item Price : " + orderItem.getPrice().getAmount()
                    + " is not valid for Product " + orderItem.getProduct().getId().getValue());
        }
    }

    private void validateTotalPrice() {
        if(price ==null || !price.isGreaterThanZero()){
            throw new OrderDomainException("Total price must be greater  than zero");
        }
    }

    private void validateInitialOrder() {
        if(orderStatus != null || getId() !=null){
            throw  new OrderDomainException("Order iss not in correct stat for initialization");
        }
    }

    private void initializeOrderItems(){
        long itemId =1;
        for(OrderItem orderItem: orderItems){
            orderItem.initializeOrderItem(super.getId(),new OrderItemId(itemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        orderItems = builder.orderItems;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> orderItems;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder orderItems(List<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
