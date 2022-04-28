package com.fx.demo.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fx.demo.Price;

public class PriceImpl implements Price {

    private int id;
    private String instrumentName;
    private BigDecimal bid;
    private BigDecimal ask;
    private LocalDateTime timestamp;

    public PriceImpl(int id, String instrumentName,
        BigDecimal bid, BigDecimal ask, LocalDateTime timestamp)
    {
        this.id = id;
        this.instrumentName = instrumentName;
        this.bid = bid;
        this.ask = ask;
        this.timestamp = timestamp;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public String instrumentName() {
        return instrumentName;
    }

    @Override
    public BigDecimal bid() {
        return bid;
    }

    @Override
    public BigDecimal ask() {
        return ask;
    }

    @Override
    public LocalDateTime timestamp() {
        return timestamp;
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
