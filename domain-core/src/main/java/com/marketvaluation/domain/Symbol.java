package com.marketvaluation.domain;

/**
 * The identifier for the index or equity being valued (e.g. {@code ^GSPC} for
 * the S&amp;P 500).
 *
 * <p>Deliberately not called "ticker", "stock", or "instrument" - see
 * CONTEXT.md.
 */
public record Symbol(String value) {

    public Symbol {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Symbol value must not be blank");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
