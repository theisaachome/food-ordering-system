package com.food.ordering.system.order.service.dataaccess.valueobject;

import java.util.Objects;
import java.util.UUID;

public class StreetAddress {

    private final UUID streetId;
    private final String street;
    private final String postalCode;
    private final String city;

    public StreetAddress(UUID streetId, String street, String postalCode, String city) {
        this.streetId = streetId;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public UUID getStreetId() {
        return streetId;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StreetAddress that)) return false;
        return Objects.equals(street, that.street) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, postalCode, city);
    }
}
