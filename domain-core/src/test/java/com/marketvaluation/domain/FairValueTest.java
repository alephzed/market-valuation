package com.marketvaluation.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FairValueTest {

    @Test
    void retainsItsValue() {
        assertEquals(new BigDecimal("4123.45"), new FairValue(new BigDecimal("4123.45")).value());
    }

    @Test
    void rejectsNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new FairValue(null));
    }
}
