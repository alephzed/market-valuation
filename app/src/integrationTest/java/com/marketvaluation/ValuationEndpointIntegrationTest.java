package com.marketvaluation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * HTTP API seam (PRD testing seam #1): drives the read endpoint through
 * MockMvc against a real Spring context backed by a Testcontainers Postgres.
 * Slice 2 proves the web adapter -> application -> domain path returns a
 * domain-shaped Valuation, with no external fetching.
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ValuationEndpointIntegrationTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    MockMvc mockMvc;

    @Test
    void returnsAValuationForAKnownSymbol() throws Exception {
        mockMvc.perform(get("/api/v1/symbols/SP500/valuation"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.symbol").value("SP500"))
                .andExpect(jsonPath("$.marketPrice").exists())
                .andExpect(jsonPath("$.scenarios", hasSize(4)))
                .andExpect(jsonPath("$.scenarios[*].scenario",
                        containsInAnyOrder("TRAILING", "FORWARD", "BLENDED", "MAX_FORWARD")))
                .andExpect(jsonPath("$.scenarios[0].fairValue").exists())
                .andExpect(jsonPath("$.scenarios[0].verdict.judgment").exists())
                .andExpect(jsonPath("$.scenarios[0].verdict.gapPercent").exists());
    }

    @Test
    void returnsProblemDetail404ForAnUnknownSymbol() throws Exception {
        mockMvc.perform(get("/api/v1/symbols/NOSUCH/valuation"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.detail").exists());
    }
}
