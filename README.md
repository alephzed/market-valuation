# Market Valuation

A system that estimates whether a market index or equity is over- or under-priced, by comparing its Market Price against a model-predicted Fair Value derived from Earnings, Dividends, and Treasury Yields.

See `CONTEXT.md` for the domain glossary and `docs/adr/` for the architectural decisions behind the design.

## Status

This is a Spring Boot modular monolith (ADR-0001, hexagonal architecture with Spring Modulith), currently in Phase 1 of the rewrite described in `docs/adr/0008-rewrite-python-prototype-to-jvm-spring.md`.

The original Python/Flask/pandas/scikit-learn prototype that proved the Valuation Model has been retired from `main`.
It is preserved at the tag [`v0-python-prototype`](../../tree/v0-python-prototype) for reference.

## Project structure

A Gradle multi-module build, with modules mapping to the bounded contexts from `CONTEXT.md`:

- `domain-core` - the shared domain core. Pure Java: no Spring, no JPA, no web dependency. Enforces the hexagonal boundary.
- `valuation` - the Valuation bounded context (core). Fair Value computation, Valuation Verdict, the Valuation aggregate.
- `market-data` - the Market Data Acquisition bounded context (supporting, anti-corruption layer).
- `calibration` - the Model Calibration bounded context (supporting).
- `identity` - the Identity bounded context (generic). Users, Watchlists, Alert configuration, RBAC.
- `app` - the application/bootstrap module. Composes the other modules, hosts the Spring Boot main class, and hosts the Spring Modulith module-boundary verification test.

Module boundaries are enforced at test time by Spring Modulith (see `app/src/test/java/com/marketvaluation/ModularityTests.java`).

## Building and testing

```
./gradlew build
```

Runs compilation, unit tests, and the Spring Modulith module-boundary verification test for every module. Requires no external services.

```
./gradlew integrationTest
```

Runs the integration tests that need a real Postgres instance, provisioned by Testcontainers and wired in automatically via `@ServiceConnection`.
This requires Docker and is not part of the `build`/`check` lifecycle, so `./gradlew build` stays green on machines without Docker.

## Requirements

- JDK 21 - provisioned automatically by the Gradle toolchain (`foojay-resolver-convention`); no manual install needed even if your system JDK is older.
- Docker - only required for `./gradlew integrationTest`.
