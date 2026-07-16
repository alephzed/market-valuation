package com.marketvaluation.valuation;

import com.marketvaluation.domain.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ValuationModuleTest {

    @Test
    void describesGivenSymbol() {
        String description = new ValuationModule().describe(new Symbol("^GSPC"));
        assertTrue(description.contains("^GSPC"));
    }
}
