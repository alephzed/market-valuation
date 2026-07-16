package com.marketvaluation.domain;

import java.math.BigDecimal;

/**
 * The Valuation Model's predicted price for a given set of inputs (Treasury
 * Yield, Dividend, Earnings). A single number.
 *
 * <p>Deliberately not called "fair market value", "calculated price", or
 * "FairValue" as a bare float - see CONTEXT.md. Modeled as a value object
 * (BigDecimal-backed) rather than a bare primitive so arithmetic on Fair
 * Value is explicit and NaN cannot silently propagate.
 */
public record FairValue(BigDecimal value) {

    public FairValue {
        if (value == null) {
            throw new IllegalArgumentException("FairValue value must not be null");
        }
    }
}
