# Modular monolith with hexagonal architecture, not microservices

The system is built as a single Spring Boot deployable organized into enforced modules (Spring Modulith) along bounded-context lines - Valuation, Market Data Acquisition, Model Calibration, Identity - with the domain core isolated from infrastructure behind ports and adapters. We chose this over microservices because the domain is small and cohesive, splitting it would multiply cold-start cost and operational surface, and hexagonal boundaries let the same application services be exposed through different inbound adapters (Spring MVC in phase 1, Lambda handlers in phase 2) without touching the domain.

## Consequences

- Module boundaries (how the code is organized) are deliberately **not** 1:1 with deployment boundaries (how functions are carved for runtime); the former follow the domain, the latter follow failure domain, cadence, and execution model.
- Tactical DDD (value objects, aggregates) is concentrated on the Valuation core and kept deliberately light in the generic Identity and DTO-heavy Market Data contexts.
