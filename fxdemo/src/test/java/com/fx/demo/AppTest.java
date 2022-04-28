package com.fx.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

import com.fx.demo.impl.CSVPriceParserImpl;
import com.fx.demo.impl.PriceTrackerImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class AppTest 
{
    private static Stream<Arguments> provideCSVPrices() {
        return Stream.of(
            Arguments.of("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001", 1),
            Arguments.of("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n"
                        + "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002", 2),
            Arguments.of("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n"
                        + "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002\n"
                        + "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002\n"
                        + "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100\n"
                        + "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110", 5)
        );
    }

    private static Stream<Arguments> provideSimulatedData() {
        return Stream.of(
            Arguments.of("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n",
                        "106, EUR/USD, 1.1010,1.2020,01-06-2020 12:01:01:010", 106,
                        -0.001f, 0.001f, new BigDecimal(1.099899), new BigDecimal(1.203202f))
        );
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("provideCSVPrices")
    public void csvParser_shouldSucceed(String csv, int expectedSize) {
        PriceParser parser = new CSVPriceParserImpl("dd-MM-yyyy HH:mm:ss:SSS");

        List<Price> results = parser.parse(csv);

        Assertions.assertTrue(results != null);
        Assertions.assertEquals(results.size(), expectedSize);
    }


    @Order(2)
    @ParameterizedTest
    @MethodSource("provideSimulatedData")
    public void priceTracker_shouldReturnLatestPrice(String firstCSV, String secondCSV, int priceId,
        float bidMargin, float askMargin, BigDecimal expectedBid, BigDecimal expectedAsk) {

        PriceParser parser = new CSVPriceParserImpl("dd-MM-yyyy HH:mm:ss:SSS");
        PriceTracker tracker = new PriceTrackerImpl(parser, bidMargin, askMargin);

        tracker.onMessage(firstCSV);
        tracker.onMessage(secondCSV);
        
        Price latestPrice = tracker.latestPrice(priceId);

        RoundingMode roundMode = RoundingMode.FLOOR;

        Assertions.assertTrue(latestPrice.bid().setScale(6, roundMode).compareTo(expectedBid.setScale(6, roundMode)) == 0);
        Assertions.assertTrue(latestPrice.ask().setScale(6, roundMode).compareTo(expectedAsk.setScale(6, roundMode)) == 0);
    }
}
