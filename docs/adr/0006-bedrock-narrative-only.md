# Bedrock generates Narrative only; it never computes valuations

Amazon Bedrock is used solely to generate a Narrative - a natural-language explanation of an already-computed Valuation. It is never placed in the path that produces or alters any number: Fair Value, Coefficients, verdicts, and all inputs stay fully deterministic. We also rejected using an LLM for data extraction from scraped pages, to keep the deterministic ingestion path free of non-determinism and per-token cost.

## Consequences

- The Narrative is derived from Valuation output and can be regenerated or omitted without affecting correctness.
- This boundary is encoded in the domain glossary (`CONTEXT.md`, "Narrative"), so the guardrail is visible to anyone reading the language, not only this ADR.
