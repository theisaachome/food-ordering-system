package com.food.ordering.system.order.service.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;
    public static  final  Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {this.amount = amount;}

    public boolean isGreaterThanZero(){
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isLessThan(Money money){
        return this.amount != null && this.amount.compareTo(money.getAmount()) >0;
    }

    public Money add(Money money){
        return new Money(setScale(this.amount.add(money.getAmount())));
    }

    public Money substract(Money money){
        return new Money(setScale(this.amount.subtract(money.getAmount())));
    }

    public Money multiply(int multiplicand){
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplicand))));
    }
    public BigDecimal getAmount() {return amount;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
    private BigDecimal setScale(BigDecimal input){
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
