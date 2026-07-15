# Step Functions orchestration over Lambdas, not a single Fargate service

The refresh pipeline (ingest Treasury, Market Data, Forward earnings, and Quote; calibrate; compute Valuation; persist) runs as ~8-10 Lambdas orchestrated by a Standard Step Functions workflow, split by failure domain, cadence, and execution model. We chose this over a single always-on Fargate service because the workload is bursty and scheduled (zero idle cost), each external source is an independent failure domain needing its own declarative retry/backoff, and the step dependencies form a DAG that Step Functions makes explicit.

## Considered Options

- **Single Fargate/App Runner service with `@Scheduled` jobs** - simpler and no cold starts, and the choice we would make under steady-state high traffic. Rejected here because it pays to idle 24/7 and would keep orchestration logic imperative.

## Consequences

- Replaces the previous design's fragile ordering, which faked step dependencies with staggered scheduler start offsets (`next_run_time + timedelta(...)`), with an explicit DAG.
- The synchronous read Lambda uses JVM + SnapStart (with snapshot priming) for acceptable cold starts; GraalVM native image is a later stretch experiment for the read path only. The async pipeline Lambdas run plain JVM, since cold start is invisible behind Step Functions.
