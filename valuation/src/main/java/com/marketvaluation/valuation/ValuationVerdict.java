package com.marketvaluation.valuation;

import java.math.BigDecimal;

/**
 * The OVERVALUED / UNDERVALUED judgment for a Symbol under one Earnings
 * Scenario, plus the percentage gap between its Market Price and its Fair
 * Value (CONTEXT.md).
 *
 * <p>Deliberately not called "valued" or "diff" - see CONTEXT.md.
 */
public record ValuationVerdict(Judgment judgment, BigDecimal gapPercent) {

    public ValuationVerdict {
        if (judgment == null) {
            throw new IllegalArgumentException("judgment must not be null");
        }
        if (gapPercent == null) {
            throw new IllegalArgumentException("gapPercent must not be null");
        }
    }
}
