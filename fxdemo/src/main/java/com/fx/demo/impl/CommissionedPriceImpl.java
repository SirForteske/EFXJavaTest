package com.fx.demo.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fx.demo.Price;

public class CommissionedPriceImpl implements Price {

    private final Price origin;
    private final BigDecimal bid;
    private final BigDecimal ask;

    public CommissionedPriceImpl(Price origin, float bidMargin, float askMargin)
    {
        this.origin = origin;
        this.bid = origin.bid().add(origin.bid().multiply(BigDecimal.valueOf(bidMargin)));
        this.ask = origin.ask().add(origin.ask().multiply(BigDecimal.valueOf(askMargin)));
    }

    @Override
    public int id() {
        return this.origin.id();
    }

    @Override
    public String instrumentName() {
        return this.origin.instrumentName();
    }

    @Override
    public BigDecimal bid() {
        return this.bid;
    }

    @Override
    public BigDecimal ask() {
        return this.ask;
    }

    @Override
    public LocalDateTime timestamp() {
        return this.origin.timestamp();
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id() + "'" +
            ", instrumentName='" + instrumentName() + "'" +
            ", bid='" + bid() + "'" +
            ", ask='" + ask() + "'" +
            ", timestamp='" + timestamp() + "'" +
            "}";
    }

}
