package com.marketvaluation;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

/**
 * Enforces the module boundaries from ADR-0001 at test time: each bounded
 * context (Valuation, Market Data Acquisition, Model Calibration, Identity)
 * may only reach into another module's declared API, never its internals.
 */
class ModularityTests {

    ApplicationModules modules = ApplicationModules.of(MarketValuationApplication.class);

    @Test
    void verifiesModularStructure() {
        modules.verify();
    }
}
