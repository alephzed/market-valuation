package com.marketvaluation.marketdata;

import com.marketvaluation.domain.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MarketDataModuleTest {

    @Test
    void describesGivenSymbol() {
        String description = new MarketDataModule().describe(new Symbol("^GSPC"));
        assertTrue(description.contains("^GSPC"));
    }
}
