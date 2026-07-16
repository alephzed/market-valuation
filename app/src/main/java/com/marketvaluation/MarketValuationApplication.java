package com.marketvaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
