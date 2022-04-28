package com.fx.demo.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fx.demo.Price;
import com.fx.demo.PriceParser;

public class CSVPriceParserImpl implements PriceParser {

    private static final String REGEX_PRICE_SEPARATOR = ",";
    private static final short PRICE_PART_ID = 0;
    private static final short PRICE_PART_INSTRUMENT = 1;
    private static final short PRICE_PART_BID = 2;
    private static final short PRICE_PART_ASK = 3;
    private static final short PRICE_PART_TIMESTAMP = 4;

    private final DateTimeFormatter dateTimeFormatter;

    public CSVPriceParserImpl(String dateFormat) {
        if(dateFormat == null || dateFormat.isEmpty())
            throw new IllegalArgumentException("'dateFormat' must have a value.");        

        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public List<Price> parse(String csv) {

        if(csv == null || csv.isEmpty())
            throw new IllegalArgumentException("'csv' argument must have a value.");

        String[] priceLines = csv.split("\n");
        List<Price> prices = new ArrayList<>();

        for(String priceLine : priceLines)
        {
            prices.add(parsePrice(priceLine));
        }

        return prices;
    }
    

    private Price parsePrice(String price) {
        String[] priceParts = price.split(REGEX_PRICE_SEPARATOR);

        if(priceParts.length != 5)
            throw new IllegalArgumentException("'price' argument format is invalid: price=" + price);

        return new PriceImpl(Integer.parseInt(priceParts[PRICE_PART_ID]), 
            priceParts[PRICE_PART_INSTRUMENT],
            new BigDecimal(Double.parseDouble(priceParts[PRICE_PART_BID])), 
            new BigDecimal(Double.parseDouble(priceParts[PRICE_PART_ASK])), 
            LocalDateTime.parse(priceParts[PRICE_PART_TIMESTAMP], this.dateTimeFormatter));
    }
}
