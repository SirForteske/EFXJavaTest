package com.fx.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fx.demo.impl.CSVPriceParserImpl;
import com.fx.demo.impl.PriceTrackerImpl;
import com.fx.demo.impl.RESTPriceImpl;

public class App 
{
    private static final String csv = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n"
    + "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002\0\n"
    + "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002\n"
    + "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100\n"
    + "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110";

    public static void main( String[] args )
    {
        
        try {
            PriceParser parser = new CSVPriceParserImpl("dd-MM-yyyy HH:mm:ss:SSS");
            PriceTracker tracker = new PriceTrackerImpl(parser, -0.001f, 0.001f);
            RESTPrice endPoint = new RESTPriceImpl(tracker);

            for(String arg : args) {
                tracker.onMessage(new String(Files.readAllBytes(Paths.get(arg))));
            }

            System.out.println("Enter a price Id to get its latest value:");

            int id = Integer.valueOf(System.console().readLine());
            System.out.println("Latest price: " + endPoint.getLatestPrice(id).toString());
            
        } catch (IOException e) {
            System.out.println("Could not read the file.");
            e.printStackTrace();
        }
    }
}
