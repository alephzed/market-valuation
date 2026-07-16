package com.marketvaluation.marketdata;

import com.marketvaluation.domain.Symbol;
import org.springframework.stereotype.Component;

/**
 * Placeholder marking the Market Data Acquisition bounded context
 * (ADR-0001). Slice 1 exists only to prove the module boundary and the
 * Gradle wiring; the outbound ports for Treasury Yield, Treasury Curve
 * Forecast, Market Data, Stock Quote, and Shiller Data are filled in by
 * later slices.
 */
@Component
public class MarketDataModule {

    public String describe(Symbol symbol) {
        return "Market Data Acquisition module placeholder for " + symbol;
    }
}
