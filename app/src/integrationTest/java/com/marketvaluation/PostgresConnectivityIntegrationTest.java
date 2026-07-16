package com.marketvaluation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Proves the {@link AbstractPostgresIntegrationTest} wiring: a Spring
 * context backed by a real, Testcontainers-provisioned Postgres instance.
 */
class PostgresConnectivityIntegrationTest extends AbstractPostgresIntegrationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void connectsToTheProvisionedPostgresContainer() {
        Integer result = jdbcTemplate.queryForObject("select 1", Integer.class);

        assertEquals(1, result);
    }
}
