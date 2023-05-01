package com.spotlight.platform.userprofile.api.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.core.json.JsonMapper;
import com.spotlight.platform.userprofile.api.web.exceptionmappers.ApiExceptionMapper;
import com.spotlight.platform.userprofile.api.web.exceptionmappers.EntityNotFoundExceptionMapper;

import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.test.jupiter.TestDropwizardApp;

@TestDropwizardApp(value = UserProfileApiApplication.class, randomPorts = true)
class UserProfileApiApplicationTest {

    @Test
    void objectMapper_IsOfSameInstance(final ObjectMapper objectMapper) {
        assertThat(objectMapper).isEqualTo(JsonMapper.getInstance());
    }

    @Test
    void exceptionMappers_AreRegistered(final Environment environment) {
        assertThat(getRegisteredSingletonClasses(environment)).containsOnlyOnce(EntityNotFoundExceptionMapper.class);
        assertThat(getRegisteredSingletonClasses(environment)).containsOnlyOnce(ApiExceptionMapper.class);
    }

    @Test
    void dummyHealthCheck_IsRegistered(final Environment environment) {
        assertThat(environment.healthChecks().getNames()).contains("preventing-startup-warning-healthcheck");
    }

    protected Set<Class<?>> getRegisteredSingletonClasses(final Environment environment) {
        return environment.jersey().getResourceConfig().getSingletons().stream().map(Object::getClass)
                .collect(Collectors.toSet());
    }
}