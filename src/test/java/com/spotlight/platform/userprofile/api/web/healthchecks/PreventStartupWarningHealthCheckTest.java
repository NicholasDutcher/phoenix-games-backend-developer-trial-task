package com.spotlight.platform.userprofile.api.web.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheck.Result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PreventStartupWarningHealthCheckTest {

    private final PreventStartupWarningHealthCheck healthCheck = new PreventStartupWarningHealthCheck();

    @Test
    void healthcheckName_isCorrect() {
        assertThat(PreventStartupWarningHealthCheck.NAME).isEqualTo("preventing-startup-warning-healthcheck");
    }

    @Test
    void healthCheckCalled_returnsHealthy() {
        final Result healthCheckResult = healthCheck.check();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(healthCheckResult.equals(HealthCheck.Result.healthy()));
    }
}