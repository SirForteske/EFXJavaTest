package com.fx.demo;

import java.util.List;

public interface PriceParser {
    List<Price> parse(String price);
}
