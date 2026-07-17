package com.marketvaluation;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Observability seam: every log line emitted while handling a request carries
 * the same trace id. Enables DEBUG for our packages so both the controller's
 * line and the request-logging filter's line are captured, then asserts they
 * share one well-formed trace id - present, and consistent across the
 * request's log lines.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "logging.level.com.marketvaluation=DEBUG")
class RequestTraceLoggingTest {

    @Autowired
    TestRestTemplate rest;

    private final Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private final ListAppender<ILoggingEvent> appender = new ListAppender<>();

    @BeforeEach
    void attachAppender() {
        appender.start();
        rootLogger.addAppender(appender);
    }

    @AfterEach
    void detachAppender() {
        rootLogger.detachAppender(appender);
        appender.stop();
    }

    @Test
    void everyLogLineForARequestCarriesOneWellFormedTraceId() {
        rest.getForEntity("/api/v1/symbols/SP500/valuation", String.class);

        List<ILoggingEvent> requestLines = appender.list.stream()
                .filter(event -> event.getLoggerName().equals(RequestLoggingFilter.class.getName())
                        || event.getLoggerName().equals(ValuationController.class.getName()))
                .toList();

        // Both the controller line and the request-logging filter line were emitted.
        assertThat(requestLines)
                .withFailMessage("expected the controller and request-log lines, got: %s", appender.list)
                .hasSize(2);

        // They are present and agree on a single, well-formed trace id.
        List<String> traceIds = requestLines.stream()
                .map(event -> event.getMDCPropertyMap().get("traceId"))
                .distinct()
                .toList();
        assertThat(traceIds).hasSize(1);
        assertThat(traceIds.get(0)).matches("[0-9a-f]{32}");
    }
}
