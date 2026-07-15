# Cognito for authentication and RBAC, replacing roll-your-own JWT

Authentication is delegated to an Amazon Cognito user pool, with the API Gateway JWT authorizer verifying tokens before requests reach any Lambda. Authorization is coarse RBAC via Cognito groups (`free`, `premium`, `admin`) surfaced as the `cognito:groups` claim and enforced with Spring Security method security inside the application. This deliberately replaces the previous hand-rolled PyJWT login and `Users` table.

## Consequences

- Credentials live in Cognito, not in Postgres; the domain schema has no password table.
- Enforcement is two-layer: API Gateway answers "is this token valid?" (keeping unauthenticated traffic off the Lambdas), while feature/role checks happen inside the application, close to the domain.
- Existing users do not port as data; they become a Cognito user-pool import or are recreated.
