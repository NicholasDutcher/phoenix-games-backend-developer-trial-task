package com.spotlight.platform.userprofile.api.core.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.spotlight.platform.userprofile.api.core.exceptions.ApiException;
import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeCollect;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeIncrement;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeReplace;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import com.spotlight.platform.userprofile.api.web.resources.UserProfileApiModel;

class UserProfileServiceTest {
        private final UserProfileDao userProfileDaoMock = mock(UserProfileDao.class);
        private final UserProfileService userProfileService = new UserProfileService(userProfileDaoMock);

        @Nested
        @DisplayName("get")
        class Get {
        @Test
        void getForExistingUser_returnsUser() {
            when(userProfileDaoMock.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixtures.USER_PROFILE));

            assertThat(userProfileService.get(UserProfileFixtures.VALID_USER_ID)).usingRecursiveComparison()
                    .isEqualTo(UserProfileFixtures.USER_PROFILE);
        }

        @Test
        void getForNonExistingUser_throwsException() {
            when(userProfileDaoMock.get(any(UserId.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userProfileService.get(UserProfileFixtures.VALID_USER_ID)).isExactlyInstanceOf(
                    EntityNotFoundException.class);
        }
        }

        @Test
        void testReplace() {
                assertThat(userProfileService
                                .createOrUpdate(UserProfileFixturesCommandTypeReplace.USER_PROFILE_API_REPLACE))
                                .isNotEqualTo(UserProfileFixturesCommandTypeReplace.EXPECTED_USER_PROFILE_REPLACE);
        }

        @Test
        void testIncrement() {
                assertThat(userProfileService
                                .createOrUpdate(UserProfileFixturesCommandTypeIncrement.USER_PROFILE_API_INCREMENT)
                                .equals(UserProfileFixturesCommandTypeIncrement.EXPECTED_USER_PROFILE_INCREMENT));
        }

        @Test
        void testCollect() {
                assertThat(userProfileService
                                .createOrUpdate(UserProfileFixturesCommandTypeCollect.USER_PROFILE_API_COLLECT)
                                .equals(UserProfileFixturesCommandTypeCollect.EXPECTED_USER_PROFILE_COLLECT));
        }

        @Test
        void testcreateOrUpdate_returnsException() {
                final ApiException apiException = assertThrows(ApiException.class,
                                () -> userProfileService.createOrUpdate(UserProfileFixtures.USER_PROFILE_API_EMPTY));
                assertEquals(apiException.getMessage(), "Command type is empty!");
                assertEquals(apiException.getResponseStatus(), Status.BAD_REQUEST);
        }

        @Test
        void testcreateOrUpdate_returnsNull() {
                try (final var userProfileValidatorMock = mockStatic(UserProfileValidator.class)) {
                        userProfileValidatorMock.when(
                                        () -> UserProfileValidator.getValidCommandType(any(UserProfileApiModel.class)))
                                        .thenReturn("null");
                        assertThat(userProfileService.createOrUpdate(UserProfileFixtures.USER_PROFILE_API)).isNull();
                }
        }

        @Test
        void testCreatingNewUserProfile() {
                try (final var userProfileValidatorMock = mockStatic(UserProfileValidator.class)) {
                        userProfileValidatorMock.when(() -> UserProfileValidator
                                        .getValidCommandType(any(UserProfileApiModel.class)))
                                        .thenReturn("collect");
                        userProfileValidatorMock.when(() -> UserProfileValidator
                                        .getValidCollectableValue(any(UserProfilePropertyValue.class)))
                                        .thenReturn(UserProfileFixturesCommandTypeCollect.PROPERTY_COLLECT_1);
                        when(userProfileDaoMock.get(any(UserId.class))).thenReturn(Optional.empty());
                        assertThat(userProfileService.createOrUpdate(UserProfileFixtures.USER_PROFILE_API).equals(
                                        UserProfileFixtures.USER_PROFILE));
                }
        }

        @Test
        void testGetingExistingUserProfile() {
            when(userProfileDaoMock.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixtures.USER_PROFILE));
                assertThat(userProfileService.createOrUpdate(UserProfileFixtures.USER_PROFILE_API).equals(UserProfileFixtures.USER_PROFILE));
        }

}