package com.food.ordering.system.order.service.application.entity;

import com.food.ordering.system.entity.Aggregate;
import com.food.ordering.system.valueobject.RestaurantId;

import java.util.List;

public class Restaurant extends Aggregate<RestaurantId> {

    private final List<Product> products;
    private boolean active;

    private Restaurant(Builder builder) {
         super.setId(builder.restaurantId);
        products = builder.products;
        setActive(builder.active);
    }
    public static Builder builder() {
        return new Builder();
    }


    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static final class Builder {
        private RestaurantId restaurantId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
