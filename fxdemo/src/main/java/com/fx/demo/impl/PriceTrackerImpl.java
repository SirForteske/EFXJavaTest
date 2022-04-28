package com.fx.demo.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fx.demo.Price;
import com.fx.demo.PriceParser;
import com.fx.demo.PriceTracker;

public class PriceTrackerImpl implements PriceTracker {

    private final PriceParser priceParser;
    private final float bidMargin;
    private final float askMargin;

    private Map<Integer, Price> latestPrices;

    public PriceTrackerImpl(PriceParser priceParser, float bidMargin, float askMargin) {
        this.priceParser = priceParser;
        this.bidMargin = bidMargin;
        this.askMargin = askMargin;

        latestPrices = new HashMap<>();
    }

    @Override
    public void onMessage(String message) {
        List<Price> prices = priceParser.parse(message);
        
        prices.forEach(price -> latestPrices.put(price.id(), new CommissionedPriceImpl(price, bidMargin, askMargin)));
    }

    @Override
    public Price latestPrice(int priceId) {
        if(latestPrices == null || !latestPrices.containsKey(priceId))
            return null;

        return latestPrices.get(priceId);
    }
    
    @Override
    public Collection<Price> latestPrices() {
        return latestPrices.values();
    }
}
