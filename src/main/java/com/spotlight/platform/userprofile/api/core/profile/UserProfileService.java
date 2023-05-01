package com.spotlight.platform.userprofile.api.core.profile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import com.spotlight.platform.userprofile.api.web.resources.UserProfileApiModel;

public class UserProfileService {
    private final UserProfileDao userProfileDao;

    @Inject
    public UserProfileService(final UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public UserProfile get(final UserId userId) {
        return userProfileDao.get(userId).orElseThrow(EntityNotFoundException::new);
    }

    public UserProfile createOrUpdate(final UserProfileApiModel userProfileApiModel) {
        switch (UserProfileValidator.getValidCommandType(userProfileApiModel)) {
            case "replace":
                return replace(userProfileApiModel);
            case "increment":
                return increment(userProfileApiModel);
            case "collect":
                return collect(userProfileApiModel);
            default:
                return null;
        }
    }

    private UserProfile replace(final UserProfileApiModel userProfileApiModel) {
        final var apiModelproperties = getApiModelProperties(userProfileApiModel);
        for (final var property : userProfileApiModel.userProfileProperties().entrySet()) {
            final var propertyKey = property.getKey();
            apiModelproperties.put(propertyKey,
                    UserProfileValidator.getValidPropertyValue(propertyKey, property.getValue()));
        }
        return creatUserProfile(userProfileApiModel.userId(), apiModelproperties);
    }

    private UserProfile increment(final UserProfileApiModel userProfileApiModel) {
        final var apiModelproperties = getApiModelProperties(userProfileApiModel);
        for (final var property : userProfileApiModel.userProfileProperties().entrySet()) {
            final var propertyKey = property.getKey();
            if (UserProfileValidator.isValidIncrementableProperty(propertyKey)) {
                if (!apiModelproperties.containsKey(propertyKey)) {
                    apiModelproperties.put(propertyKey, UserProfilePropertyValue.valueOf(0));
                }
                apiModelproperties.put(propertyKey,
                        UserProfilePropertyValue.valueOf(
                                UserProfileValidator.getValidIncrementableValue(apiModelproperties.get(propertyKey))
                                        + UserProfileValidator
                                                .getValidIncrementableValue(property.getValue())));
            }
        }
        return creatUserProfile(userProfileApiModel.userId(), apiModelproperties);
    }

    private UserProfile collect(final UserProfileApiModel userProfileApiModel) {
        final var apiModelproperties = getApiModelProperties(userProfileApiModel);
        for (final var property : userProfileApiModel.userProfileProperties().entrySet()) {
            final var propertyKey = property.getKey();
            if (UserProfileValidator.isValidCollectableProperty(propertyKey)) {
                if (!apiModelproperties.containsKey(propertyKey)) {
                    apiModelproperties.put(propertyKey, UserProfilePropertyValue.valueOf(new ArrayList<String>()));
                }
                final var validValues = UserProfileValidator.getValidCollectableValue(
                        apiModelproperties.get(propertyKey));
                validValues.addAll(UserProfileValidator.getValidCollectableValue(property.getValue()));
                apiModelproperties.put(propertyKey, UserProfilePropertyValue.valueOf(validValues));
            }
        }
        return creatUserProfile(userProfileApiModel.userId(), apiModelproperties);
    }

    private Map<UserProfilePropertyName, UserProfilePropertyValue> getApiModelProperties(
            final UserProfileApiModel userProfileApiModel) {
        return new HashMap<>(getUserProfileFromDaoOrCreateWhenMissing(userProfileApiModel).userProfileProperties());
    }

    private UserProfile getUserProfileFromDaoOrCreateWhenMissing(final UserProfileApiModel userProfileApiModel) {
        try {
            return get(userProfileApiModel.userId());
        } catch (final EntityNotFoundException e) {
            return creatUserProfile(userProfileApiModel.userId(), new HashMap<>());
        }
    }

    private UserProfile creatUserProfile(final UserId userId,
            final Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties) {
        final UserProfile newUserProfile = new UserProfile(
                UserProfileValidator.getValidUserId(userId), Instant.now(), userProfileProperties);
        userProfileDao.put(newUserProfile);
        return newUserProfile;
    }

}