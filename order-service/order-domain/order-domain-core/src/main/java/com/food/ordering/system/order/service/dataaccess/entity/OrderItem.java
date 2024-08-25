package com.food.ordering.system.order.service.dataaccess.entity;

import com.food.ordering.system.entity.BaseEntity;
import com.food.ordering.system.order.service.dataaccess.valueobject.OrderItemId;
import com.food.ordering.system.valueobject.Money;
import com.food.ordering.system.valueobject.OrderId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final Integer quantity;
    private final Money price;
    private final Money subTotal;

     void initializeOrderItem(OrderId orderId,OrderItemId orderItemId){
        this.orderId = orderId;
        super.setId(orderItemId);
    }
    boolean isPriceValid(){
         return  price.isGreaterThanZero() &&
                 price.equals(product.getPrice()) &&
                 price.multiply(quantity).equals(subTotal);
    }
    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);
        subTotal = builder.subTotal;
        price = builder.price;
        quantity = builder.quantity;
        product = builder.product;
    }

    public static Builder builder() {
        return new Builder();
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public static final class Builder {
        private Money subTotal;
        private Money price;
        private Integer quantity;
        private Product product;
        private OrderItemId orderItemId;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder products(Product val) {
            product = val;
            return this;
        }

        public Builder orderItemId(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
