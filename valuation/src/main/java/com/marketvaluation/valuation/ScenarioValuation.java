package com.marketvaluation.valuation;

import com.marketvaluation.domain.FairValue;

/**
 * One Earnings Scenario's result within a Valuation: the Fair Value the
 * Valuation Model produced for that scenario and the resulting Valuation
 * Verdict.
 */
public record ScenarioValuation(EarningsScenario scenario, FairValue fairValue, ValuationVerdict verdict) {

    public ScenarioValuation {
        if (scenario == null) {
            throw new IllegalArgumentException("scenario must not be null");
        }
        if (fairValue == null) {
            throw new IllegalArgumentException("fairValue must not be null");
        }
        if (verdict == null) {
            throw new IllegalArgumentException("verdict must not be null");
        }
    }
}
