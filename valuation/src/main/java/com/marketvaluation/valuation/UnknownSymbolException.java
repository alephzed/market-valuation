package com.marketvaluation.valuation;

import com.marketvaluation.domain.Symbol;

/**
 * Raised when a Valuation is requested for a Symbol the system cannot value.
 * The web adapter translates this into an RFC 7807 {@code 404} response.
 */
public class UnknownSymbolException extends RuntimeException {

    private final transient Symbol symbol;

    public UnknownSymbolException(Symbol symbol) {
        super("No Valuation is available for symbol " + symbol);
        this.symbol = symbol;
    }

    public Symbol symbol() {
        return symbol;
    }
}
