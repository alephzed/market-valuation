package com.marketvaluation.valuation;

import com.marketvaluation.domain.Symbol;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * The complete assessment produced for one Symbol at one point in time,
 * bundling the four Earnings Scenario results and their Valuation Verdicts
 * (CONTEXT.md). The aggregate of the Valuation bounded context.
 *
 * <p>Deliberately not called "stock valuation" - see CONTEXT.md.
 */
public record Valuation(Symbol symbol, Instant asOf, BigDecimal marketPrice, List<ScenarioValuation> scenarios) {

    public Valuation {
        if (symbol == null) {
            throw new IllegalArgumentException("symbol must not be null");
        }
        if (scenarios == null) {
            throw new IllegalArgumentException("scenarios must not be null");
        }
        scenarios = List.copyOf(scenarios);
    }
}
