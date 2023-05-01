package com.spotlight.platform.userprofile.api.core.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

import com.spotlight.platform.userprofile.api.core.exceptions.ApiException;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeCollect;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeIncrement;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import com.spotlight.platform.userprofile.api.web.resources.UserProfileApiModel;

public class UserProfileValidatorTest {

    @Test
    void testValidUserId() {
        assertThat(UserProfileValidator.getValidUserId(UserProfileFixtures.VALID_USER_ID)).isEqualTo(
                UserProfileFixtures.VALID_USER_ID);
    }

    @Test
    void testInvalidUserId() {
        final ApiException apiException = assertThrows(ApiException.class,
                () -> UserProfileValidator.getValidUserId(UserProfileFixtures.INVALID_USER_ID));
        assertEquals(apiException.getMessage(), "Not a valid user ID!");
        assertEquals(apiException.getResponseStatus(), Status.BAD_REQUEST);
    }

    @Test
    void testValidCommandTypeReplace() {
        final var userProfileApiModel = new UserProfileApiModel(null, "replace", null);
        assertThat(UserProfileValidator.getValidCommandType(userProfileApiModel)).isEqualTo("replace");
    }

    @Test
    void testValidCommandTypeIncrement() {
        final var userProfileApiModel = new UserProfileApiModel(null, "increment", null);
        assertThat(UserProfileValidator.getValidCommandType(userProfileApiModel)).isEqualTo("increment");
    }

    @Test
    void testValidCommandTypeCollect() {
        final var userProfileApiModel = new UserProfileApiModel(null, "collect", null);
        assertThat(UserProfileValidator.getValidCommandType(userProfileApiModel)).isEqualTo("collect");
    }

    @Test
    void testInvalidCommandType() {
        final var userProfileApiModel = new UserProfileApiModel(null, "loot", null);
        final ApiException apiException = assertThrows(ApiException.class,
                () -> UserProfileValidator.getValidCommandType(userProfileApiModel));
        assertEquals(apiException.getMessage(), "loot is not a valid command type!");
        assertEquals(apiException.getResponseStatus(), Status.BAD_REQUEST);
    }

    @Test
    void testValidCollectableProperty() {
        assertThat(
                UserProfileValidator.getValidPropertyValue(
                        UserProfileFixturesCommandTypeCollect.PROPERTY_NAME_COLLECT_1,
                        UserProfileFixturesCommandTypeCollect.PROPERTY_VALUE_COLLECT_1))
                .isEqualTo(UserProfilePropertyValue.valueOf(UserProfileFixturesCommandTypeCollect.PROPERTY_COLLECT_1));
    }

    @Test
    void testInvalidCollectableProperty() {
        final ApiException apiException = assertThrows(ApiException.class,
                () -> UserProfileValidator.getValidPropertyValue(
                        UserProfileFixturesCommandTypeCollect.PROPERTY_NAME_COLLECT_1,
                        UserProfileFixturesCommandTypeIncrement.PROPERTY_VALUE_INCREMENT_1));
        assertEquals(apiException.getMessage(), "Property value is not collectable!");
        assertEquals(apiException.getResponseStatus(), Status.BAD_REQUEST);
    }

    @Test
    void testValidIncrementableProperty() {
        assertThat(
                UserProfileValidator.getValidPropertyValue(
                        UserProfileFixturesCommandTypeIncrement.PROPERTY_NAME_INCREMENT_1,
                        UserProfileFixturesCommandTypeIncrement.PROPERTY_VALUE_INCREMENT_1))
                .isEqualTo(
                        UserProfilePropertyValue.valueOf(UserProfileFixturesCommandTypeIncrement.PROPERTY_INCREMENT_1));
    }

    @Test
    void testInvalidIncrementableProperty() {
        final ApiException apiException = assertThrows(ApiException.class,
                () -> UserProfileValidator.getValidIncrementableValue(UserProfileFixtures.PROPERTY_MISC_VALUE_1));
        assertEquals(apiException.getMessage(), "Property value is not a number!");
        assertEquals(apiException.getResponseStatus(), Status.BAD_REQUEST);
    }

    @Test
    void testValidMiscProperty() {
        final UserProfilePropertyValue propertyValue = UserProfileFixtures.PROPERTY_MISC_VALUE_1;
        assertThat(UserProfileValidator.getValidPropertyValue(UserProfileFixtures.PROPERTY_NAME_MISC_1, propertyValue))
                .isEqualTo(propertyValue);
    }

    @Test
    void testMiscPropertyAgainstCollect() {
        final UserProfilePropertyName propertyName = UserProfileFixtures.PROPERTY_NAME_MISC_1;
        final ApiException apiException = assertThrows(ApiException.class,
                () -> UserProfileValidator.isValidCollectableProperty(propertyName));
        assertEquals(apiException.getMessage(), propertyName + " is not a valid property!");
        assertEquals(apiException.getResponseStatus(), Status.BAD_REQUEST);
    }

    @Test
    void testMiscPropertyAgainstIncrement() {
        final UserProfilePropertyName propertyName = UserProfileFixtures.PROPERTY_NAME_MISC_1;
        final ApiException apiException = assertThrows(ApiException.class,
                () -> UserProfileValidator.isValidIncrementableProperty(propertyName));
        assertEquals(apiException.getMessage(), propertyName + " is not a valid property!");
        assertEquals(apiException.getResponseStatus(), Status.BAD_REQUEST);
    }

}
