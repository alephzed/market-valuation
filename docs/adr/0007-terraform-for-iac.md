# Terraform for infrastructure as code, not CDK-in-Java

All AWS infrastructure (Lambdas, Step Functions, API Gateway, RDS, DynamoDB, EventBridge, Cognito) is defined in Terraform. We preferred CDK-in-Java for single-language cohesion and testable typed constructs, but chose Terraform because it is the more transferable, more widely used skill for the target role - the deciding factor for a job-focused build.

## Consequences

- Terraform does not build application code: the pipeline is `Gradle build` -> upload artifacts to S3/ECR -> `terraform apply` referencing them by version/digest.
- The ~8-10 Lambdas are defined via one reusable Terraform module (function + IAM role + log group + alarms) instantiated per function, rather than duplicated resource blocks.
