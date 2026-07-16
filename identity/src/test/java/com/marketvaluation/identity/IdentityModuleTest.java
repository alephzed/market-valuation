package com.marketvaluation.identity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class IdentityModuleTest {

    @Test
    void describesItself() {
        assertFalse(new IdentityModule().describe().isBlank());
    }
}
