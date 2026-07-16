package com.marketvaluation;

import com.marketvaluation.domain.SymbolCatalog;
import com.marketvaluation.valuation.GetValuation;
import com.marketvaluation.valuation.UnknownSymbolException;
import com.marketvaluation.valuation.Valuation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inbound web adapter for the read path: exposes the current Valuation for a
 * Symbol. Stateless and performs no external fetching - it serves the
 * Valuation produced by the {@link GetValuation} application service
 * (PRD read/refresh split). The clean public Symbol id in the URL is
 * normalized to its canonical form at this boundary via {@link SymbolCatalog}.
 */
@RestController
@RequestMapping("/api/v1/symbols")
public class ValuationController {

    private final GetValuation getValuation;
    private final SymbolCatalog symbolCatalog;

    public ValuationController(GetValuation getValuation, SymbolCatalog symbolCatalog) {
        this.getValuation = getValuation;
        this.symbolCatalog = symbolCatalog;
    }

    @GetMapping("/{symbol}/valuation")
    public ValuationResponse valuation(@PathVariable String symbol) {
        Valuation valuation = getValuation.forSymbol(symbolCatalog.fromPublicId(symbol));
        return ValuationResponse.from(valuation, symbol);
    }

    @ExceptionHandler(UnknownSymbolException.class)
    ProblemDetail handleUnknownSymbol(UnknownSymbolException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Unknown Symbol");
        return problem;
    }
}
