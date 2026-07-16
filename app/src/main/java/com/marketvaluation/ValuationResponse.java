package com.marketvaluation;

import com.marketvaluation.valuation.Valuation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Read-path DTO for a Valuation (ADR-0001: responses are proper DTOs mapped
 * from the domain, not hand-assembled JSON). Carries the public Symbol id,
 * the Market Price it was compared against, and one entry per Earnings
 * Scenario.
 */
public record ValuationResponse(
        String symbol,
        Instant asOf,
        BigDecimal marketPrice,
        List<ScenarioResponse> scenarios) {

    public static ValuationResponse from(Valuation valuation, String publicSymbolId) {
        List<ScenarioResponse> scenarios = valuation.scenarios().stream()
                .map(scenario -> new ScenarioResponse(
                        scenario.scenario().name(),
                        scenario.fairValue().value(),
                        new VerdictResponse(
                                scenario.verdict().judgment().name(),
                                scenario.verdict().gapPercent())))
                .toList();
        return new ValuationResponse(publicSymbolId, valuation.asOf(), valuation.marketPrice(), scenarios);
    }

    public record ScenarioResponse(String scenario, BigDecimal fairValue, VerdictResponse verdict) {
    }

    public record VerdictResponse(String judgment, BigDecimal gapPercent) {
    }
}
