package com.marketvaluation;

import com.marketvaluation.domain.SymbolCatalog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Composition root for the modular monolith (ADR-0001). Its package is the
 * base package Spring Modulith scans from: each of {@code valuation},
 * {@code marketdata}, {@code calibration}, {@code identity}, and
 * {@code domain} is a direct sibling package and therefore a distinct
 * module boundary that {@link ModularityTests} verifies.
 */
@SpringBootApplication
public class MarketValuationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketValuationApplication.class, args);
    }

    /**
     * The {@link SymbolCatalog} lives in the pure domain core (no Spring), so
     * the composition root exposes it as a bean for the web adapter to inject.
     */
    @Bean
    SymbolCatalog symbolCatalog() {
        return new SymbolCatalog();
    }
}
