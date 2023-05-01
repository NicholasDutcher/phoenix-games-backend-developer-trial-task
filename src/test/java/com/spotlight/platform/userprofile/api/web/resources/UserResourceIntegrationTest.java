package com.spotlight.platform.userprofile.api.web.resources;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeCollect;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeIncrement;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeReplace;
import com.spotlight.platform.userprofile.api.web.UserProfileApiApplication;

import ru.vyarus.dropwizard.guice.test.ClientSupport;
import ru.vyarus.dropwizard.guice.test.jupiter.ext.TestDropwizardAppExtension;

@Execution(ExecutionMode.SAME_THREAD)
class UserResourceIntegrationTest {

    @RegisterExtension
    static TestDropwizardAppExtension APP = TestDropwizardAppExtension.forApp(UserProfileApiApplication.class)
            .randomPorts().hooks(builder -> builder.modulesOverride(new AbstractModule() {
                @Provides
                @Singleton
                public UserProfileDao getUserProfileDao() {
                    return mock(UserProfileDao.class);
                }
            })).randomPorts().create();

    @BeforeEach
    void beforeEach(final UserProfileDao userProfileDao) {
        reset(userProfileDao);
    }

    @Nested
    @DisplayName("getUserProfile")
    class GetUserProfile {
        private static final String USER_ID_PATH_PARAM = "userId";
        private static final String URL = "/users/{%s}/profile".formatted(USER_ID_PATH_PARAM);

        @Test
        void existingUser_correctObjectIsReturned(final ClientSupport client, final UserProfileDao userProfileDao) {
            when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixtures.USER_PROFILE));

            final var response = client.targetRest().path(URL).resolveTemplate(USER_ID_PATH_PARAM, UserProfileFixtures.VALID_USER_ID).request().get();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
            assertThatJson(response.readEntity(UserProfile.class)).isEqualTo(UserProfileFixtures.SERIALIZED_USER_PROFILE);
        }

        @Test
        void nonExistingUser_returns404(final ClientSupport client, final UserProfileDao userProfileDao) {
            when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.empty());

            final var response = client.targetRest().path(URL).resolveTemplate(USER_ID_PATH_PARAM, UserProfileFixtures.VALID_USER_ID).request().get();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND_404);
        }

        @Test
        void validationFailed_returns400(final ClientSupport client) {
            final var response = client.targetRest()
                    .path(URL)
                    .resolveTemplate(USER_ID_PATH_PARAM, UserProfileFixtures.INVALID_USER_ID)
                    .request()
                    .get();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST_400);
        }

        @Test
        void unhandledExceptionOccured_returns500(final ClientSupport client, final UserProfileDao userProfileDao) {
            when(userProfileDao.get(any(UserId.class))).thenThrow(new RuntimeException("Some unhandled exception"));

            final var response = client.targetRest().path(URL).resolveTemplate(USER_ID_PATH_PARAM, UserProfileFixtures.VALID_USER_ID).request().get();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    }

    @Nested
    @DisplayName("postUserProfile")
    class PostUserProfile {
        private static final String URL = "/users/update";

      @Test
      void test_replace(final ClientSupport client, final UserProfileDao userProfileDao) {
      when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixturesCommandTypeReplace.INITIAL_USER_PROFILE_REPLACE));
      final var response =
      client.targetRest(URL).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
      .post(Entity.entity(UserProfileFixturesCommandTypeReplace.SERIALIZED_USER_PROFILE_REPLACE,MediaType.APPLICATION_JSON));
      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
      assertThatJson(response.readEntity(UserProfile.class)).whenIgnoringPaths("latestUpdateTime")
      .isEqualTo(UserProfileFixturesCommandTypeReplace.SERIALIZED_USER_PROFILE_REPLACE_RESPONSE);
      }

        @Test
      void test_ingrement(final ClientSupport client, final UserProfileDao userProfileDao) {
      when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixturesCommandTypeIncrement.INITIAL_USER_PROFILE_INCREMENT));
      final var response =
      client.targetRest(URL).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
      .post(Entity.entity(UserProfileFixturesCommandTypeIncrement.SERIALIZED_USER_PROFILE_INCREMENT,MediaType.APPLICATION_JSON));
      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
      assertThatJson(response.readEntity(UserProfile.class)).whenIgnoringPaths("latestUpdateTime")
      .isEqualTo(UserProfileFixturesCommandTypeIncrement.SERIALIZED_USER_PROFILE_INCREMENT_RESPONSE);
      }

        @Test
      void test_collect(final ClientSupport client, final UserProfileDao userProfileDao) {
      when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixturesCommandTypeCollect.INITIAL_USER_PROFILE_COLLECT));
      final var response =
      client.targetRest(URL).request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
      .post(Entity.entity(UserProfileFixturesCommandTypeCollect.SERIALIZED_USER_PROFILE_COLLECT,MediaType.APPLICATION_JSON));
      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
      assertThatJson(response.readEntity(UserProfile.class)).whenIgnoringPaths("latestUpdateTime")
      .isEqualTo(UserProfileFixturesCommandTypeCollect.SERIALIZED_USER_PROFILE_COLLECT_RESPONSE);
      }

    }

}