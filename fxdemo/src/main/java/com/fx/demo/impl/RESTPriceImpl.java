package com.fx.demo.impl;

import java.util.Collection;

import com.fx.demo.Price;
import com.fx.demo.PriceTracker;
import com.fx.demo.RESTPrice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("prices")
public class RESTPriceImpl implements RESTPrice {

    private final PriceTracker priceTracker;

    @Autowired
    public RESTPriceImpl(final PriceTracker priceTracker) {
        this.priceTracker = priceTracker;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Override
    public Price getLatestPrice(@PathVariable int id) {
        return this.priceTracker.latestPrice(id);
    }

    @GetMapping(value = "/all", produces = "application/json")
    @Override
    public Collection<Price> getLatestPrices() {
        return this.priceTracker.latestPrices();
    }
    

}
