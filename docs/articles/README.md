# Article series: From Python Prototype to Production JVM

A weekly LinkedIn series documenting the rewrite of the market-valuation engine from a Python/Flask prototype to a JVM/Spring Boot modular monolith, then to an AWS serverless architecture.
Each article covers one design decision - what it was, why it was made, and the trade-off - and most map directly to an Architecture Decision Record in [`../adr/`](../adr/).

The thesis of the whole series: **the tool should match the phase of the problem.** Python was right for discovering the model; the JVM is right for productionising it.

## Editorial calendar

| Wk | Article | Source | Status |
|----|---------|--------|--------|
| 1 | [From prototype to product: why I'm rewriting a working Python app in Java](01-from-prototype-to-product.md) | ADR-0008 | Draft |
| 2 | Sharpening the language: a DDD glossary and killing "float-soup" | CONTEXT.md | Planned |
| 3 | Hexagonal + modular monolith (and why not microservices) | ADR-0001 | Planned |
| 4 | "My ML model is 4 numbers": reimplementing scikit-learn in Java | ADR-0005 | Planned |
| 5 | One source of truth: escaping the in-memory-cache trap | ADR-0002 | Planned |
| 6 | Taming flaky data: resilience at the anti-corruption layer | resilience slices | Planned |
| 7 | RESTful by design: modeling resources, not RPC | endpoint reshape | Planned |
| 8 | Observability from day one: tracing a valuation | slice 3 | Planned |
| 9 | Virtual threads over reactive: live updates without WebFlux | SSE decision | Planned |
| 10 | Step Functions over cron-and-sleep: an explicit pipeline DAG | ADR-0003 | Planned |
| 11 | Polyglot persistence: DynamoDB as a derived serving layer | ADR-0002 | Planned |
| 12 | Cold starts: making Spring Boot viable on Lambda with SnapStart | packaging | Planned |
| 13 | Auth done right: Cognito over roll-your-own JWT | ADR-0004 | Planned |
| 14 | Where AI actually fits: Bedrock explains, never computes | ADR-0006 | Planned |

## Conventions

- Each article is self-contained but advances the arc.
- Code snippets come from the actual build, not invented examples.
- One decision per article; lead with the problem, land the trade-off honestly.
