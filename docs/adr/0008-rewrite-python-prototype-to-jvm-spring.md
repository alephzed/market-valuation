# Rewrite from Python/Flask prototype to JVM/Spring Boot

The existing market-valuation system is a Python/Flask/pandas/scikit-learn prototype that successfully proved a simple linear model (earnings, dividends, treasury yield -> fair value) estimates whether the market is over- or under-valued. With that model proven - and, as it turns out, trivial (four Coefficients) - the engineering problem has shifted from *discovering a model* to *running a correct, resilient, always-on service*. We are rewriting on the JVM/Spring Boot because that phase's demands are first-class in the Spring ecosystem and absent or hand-rolled in the prototype: compile-time correctness guarantees for financial calculations (value objects and static types, versus the prototype's positional bare floats and silent `NaN` propagation), declarative resilience against a dozen flaky scraped sources, robust concurrency for fan-out ingestion and live serving, and enforced modular boundaries for long-term maintainability.

The framing is deliberate: Python was the right tool for the prototype phase; the JVM is the right tool for the product phase. This is a decision about matching the tool to the phase of the problem, not a claim that Java is universally superior.

## Considered Options

- **Stay on Python, modernize to FastAPI + Pydantic + typed services.** The cheapest path, and it keeps the data-science ecosystem. Rejected because the model no longer needs that ecosystem (it is four coefficients), and dynamic typing remains a liability for correctness-critical financial math even with Pydantic at the edges.
- **Keep Python for the model, add a thin service layer.** Rejected: there is no meaningful ML left to keep in Python (see [ADR-0005](0005-reimplement-model-in-java.md)); a polyglot split would add a second runtime for no analytical benefit.

## Consequences

- The pandas/scikit-learn data-wrangling and the scrapers must be reimplemented (Apache POI, Jsoup, Commons Math). This is real effort, tracked as Phase 1 slices, and is the bulk of the work.
- The prototype's strengths (fast exploration, the data-science stack) are explicitly acknowledged as correct for the phase they served; this ADR does not disparage them.
