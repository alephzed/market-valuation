package com.marketvaluation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base class for integration tests that need a real Postgres instance
 * (ADR-0002: Postgres is the system of record). The container is
 * provisioned by Testcontainers and wired into the Spring context
 * automatically via {@code @ServiceConnection} (Spring Boot 3.1+ style) -
 * no manual datasource properties required.
 *
 * <p>Requires Docker. This lives in the {@code integrationTest} source set
 * and is run explicitly with {@code ./gradlew integrationTest}; it is not
 * part of the default {@code build}/{@code check} lifecycle so that
 * {@code ./gradlew build} stays green on machines without Docker.
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class AbstractPostgresIntegrationTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
}
