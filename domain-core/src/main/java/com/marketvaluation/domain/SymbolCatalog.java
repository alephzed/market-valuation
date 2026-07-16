package com.marketvaluation.domain;

import java.util.Map;

/**
 * Normalizes the clean public Symbol ids used in URLs to their internal
 * canonical form. Known indices carry a friendly alias (e.g. {@code SP500}
 * for the S&amp;P 500's canonical {@code ^GSPC}) so URLs never carry encoded
 * carets; ordinary tickers pass through unchanged.
 */
public final class SymbolCatalog {

    private static final Map<String, String> PUBLIC_TO_CANONICAL = Map.of("SP500", "^GSPC");

    public Symbol fromPublicId(String publicId) {
        return new Symbol(PUBLIC_TO_CANONICAL.getOrDefault(publicId, publicId));
    }
}
