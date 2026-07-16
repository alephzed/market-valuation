package com.marketvaluation.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SymbolCatalogTest {

    private final SymbolCatalog catalog = new SymbolCatalog();

    @Test
    void mapsAKnownIndexAliasToItsCanonicalSymbol() {
        assertEquals(new Symbol("^GSPC"), catalog.fromPublicId("SP500"));
    }

    @Test
    void passesAnOrdinaryTickerThroughUnchanged() {
        assertEquals(new Symbol("AAPL"), catalog.fromPublicId("AAPL"));
    }
}
