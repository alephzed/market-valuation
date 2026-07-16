package com.marketvaluation.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SymbolTest {

    @Test
    void retainsItsValue() {
        assertEquals("^GSPC", new Symbol("^GSPC").value());
    }

    @Test
    void rejectsBlankValue() {
        assertThrows(IllegalArgumentException.class, () -> new Symbol(" "));
    }

    @Test
    void rejectsNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new Symbol(null));
    }
}
