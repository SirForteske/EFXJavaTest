package com.fx.demo;

import java.util.Collection;

public interface RESTPrice {
    
    public Price getLatestPrice(int id);

    public Collection<Price> getLatestPrices();
}
