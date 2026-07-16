package com.marketvaluation.valuation;

import com.marketvaluation.domain.Symbol;
import org.springframework.stereotype.Component;

/**
 * Placeholder marking the Valuation bounded context (ADR-0001). Slice 1
 * exists only to prove the module boundary and the Gradle wiring; the Fair
 * Value computation across the four Earnings Scenarios, the Valuation
 * Verdict, and the Valuation aggregate are filled in by later slices.
 */
@Component
public class ValuationModule {

    public String describe(Symbol symbol) {
        return "Valuation module placeholder for " + symbol;
    }
}
