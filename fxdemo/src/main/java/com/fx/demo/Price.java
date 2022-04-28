package com.fx.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Price {
    
    int id();

    String instrumentName();

    BigDecimal bid();

    BigDecimal ask();

    LocalDateTime timestamp();
}
