package com.fx.demo;

import java.util.Collection;

public interface PriceTracker {
    
    void onMessage(String message);

    Price latestPrice(int priceId);

    Collection<Price> latestPrices();
    
}
