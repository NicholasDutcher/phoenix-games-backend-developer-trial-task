package com.spotlight.platform.userprofile.api.web.resources;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

public record UserProfileApiModel(@JsonProperty UserId userId,
                @JsonProperty("type") @JsonFormat(shape = JsonFormat.Shape.STRING) String commandType,
                @JsonProperty("properties") Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties) {
}