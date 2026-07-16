package com.marketvaluation.valuation;

import com.marketvaluation.domain.FairValue;
import com.marketvaluation.domain.Symbol;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * Application service (input port) for reading the Valuation of a Symbol.
 *
 * <p>Slice 2 returns a stubbed Valuation to prove the web adapter ->
 * application -> domain path end to end. It performs no external fetching and
 * no computation - later slices replace the stub with real Fair Value
 * computation across the four Earnings Scenarios.
 */
@Service
public class GetValuation {

    private static final Symbol SP500 = new Symbol("^GSPC");

    public Valuation forSymbol(Symbol symbol) {
        if (!SP500.equals(symbol)) {
            throw new UnknownSymbolException(symbol);
        }
        BigDecimal marketPrice = new BigDecimal("5000.00");
        List<ScenarioValuation> scenarios = Arrays.stream(EarningsScenario.values())
                .map(scenario -> new ScenarioValuation(
                        scenario,
                        new FairValue(new BigDecimal("4800.00")),
                        new ValuationVerdict(Judgment.OVERVALUED, new BigDecimal("4.17"))))
                .toList();
        return new Valuation(symbol, Instant.now(), marketPrice, scenarios);
    }
}
