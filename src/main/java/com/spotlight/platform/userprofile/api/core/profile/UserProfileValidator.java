package com.spotlight.platform.userprofile.api.core.profile;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response.Status;

import org.eclipse.jetty.util.StringUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.core.exceptions.ApiException;
import com.spotlight.platform.userprofile.api.core.json.JsonMapper;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import com.spotlight.platform.userprofile.api.web.resources.UserProfileApiModel;

final public class UserProfileValidator {

    private UserProfileValidator() {
        // For Code Coverage
    }

    private static final String userIdPattern = "[a-z]{2}[a-z0-9]{6}\\-[a-z0-9]{4}\\-[a-z0-9]{4}\\-[a-z0-9]{4}\\-[a-z0-9]{12}";

    private static final Set<String> allowedCommandTypes = Set.of("replace", "increment", "collect");

    private static final Set<UserProfilePropertyName> incrementableProperties = Set.of(
            UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyName.valueOf("currentGems"),
            UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyName.valueOf("questsNotCompleted"),
            UserProfilePropertyName.valueOf("numberOfLogins"));
    private static final Set<UserProfilePropertyName> collectableProperties = Set.of(
            UserProfilePropertyName.valueOf("inventory"), UserProfilePropertyName.valueOf("tools"));
    private static final Set<UserProfilePropertyName> otherProperties = Set
            .of(UserProfilePropertyName.valueOf("averagePlayTimePerWeek"));

    public static UserId getValidUserId(final UserId userId) {
        if (!Pattern.matches(userIdPattern, userId.toString())) {
            throw new ApiException("Not a valid user ID!", Status.BAD_REQUEST);
        }
        return userId;
    }

    public static String getValidCommandType(final UserProfileApiModel userProfileApiModel) {
        final String commandType = userProfileApiModel.commandType();
        if (StringUtil.isBlank(commandType)) {
            throw new ApiException("Command type is empty!", Status.BAD_REQUEST);
        }
        if (!allowedCommandTypes.contains(commandType)) {
            throw new ApiException(commandType + " is not a valid command type!", Status.BAD_REQUEST);
        }
        return commandType;
    }

    public static UserProfilePropertyValue getValidPropertyValue(final UserProfilePropertyName propertyName,
            final UserProfilePropertyValue propertyValue) {
        if (incrementableProperties.contains(propertyName)) {
            return UserProfilePropertyValue.valueOf(getValidIncrementableValue(propertyValue));
        }
        if (collectableProperties.contains(propertyName)) {
            return UserProfilePropertyValue.valueOf(getValidCollectableValue(propertyValue));
        }
        validate(otherProperties, propertyName);
        return propertyValue;
    }

    public static boolean isValidIncrementableProperty(final UserProfilePropertyName propertyName) {
        validate(incrementableProperties, propertyName);
        return true;
    }

    public static long getValidIncrementableValue(final UserProfilePropertyValue propertyValue) {
        try {
            final ObjectMapper jsonMapper = JsonMapper.getInstance();
            return jsonMapper.readValue(jsonMapper.writeValueAsString(propertyValue), Long.class);
        } catch (final JsonProcessingException e) {
            throw new ApiException("Property value is not a number!", Status.BAD_REQUEST);
        }
    }

    public static boolean isValidCollectableProperty(final UserProfilePropertyName propertyName) {
        validate(collectableProperties, propertyName);
        return true;
    }

    public static List<String> getValidCollectableValue(final UserProfilePropertyValue propertyValue) {
        try {
            final ObjectMapper jsonMapper = JsonMapper.getInstance();
            return jsonMapper.readValue(
                    jsonMapper.writeValueAsString(propertyValue), new TypeReference<List<String>>() {
                    });
        } catch (final JsonProcessingException e) {
            throw new ApiException("Property value is not collectable!", Status.BAD_REQUEST);
        }
    }

    private static void validate(final Set<UserProfilePropertyName> allowedPropertyName,
            final UserProfilePropertyName propertyName) {
        if (!allowedPropertyName.contains(propertyName)) {
            throw new ApiException(propertyName + " is not a valid property!", Status.BAD_REQUEST);
        }
    }

}
