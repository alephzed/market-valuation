# Postgres is the system of record; DynamoDB is a derived serving layer

Postgres holds all authoritative state - Coefficients, the historical valuation series, and relational/range-queried data. DynamoDB (introduced in the serverless phase) is a derived, disposable serving layer holding the latest Valuation and latest Stock Quote as point-read items, with TTL on the quote. It exists because moving to Lambda removes the in-process cache that previously served hot reads.

## Consequences

- DynamoDB must be fully rebuildable by replaying from Postgres; it is never a source of truth and the two must never be able to disagree about who owns the data.
- The read path serves from DynamoDB on the hot path and falls back to Postgres for relational/historical queries.
