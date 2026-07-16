package com.marketvaluation.valuation;

/**
 * A choice of which earnings figure feeds the Valuation Model; each scenario
 * yields its own Fair Value and Valuation Verdict within a Valuation
 * (CONTEXT.md).
 */
public enum EarningsScenario {
    TRAILING,
    FORWARD,
    BLENDED,
    MAX_FORWARD
}
