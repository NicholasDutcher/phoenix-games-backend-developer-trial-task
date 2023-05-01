package com.spotlight.platform.userprofile.api.model.profile.primitives;

import java.time.Instant;
import java.util.Map;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.web.resources.UserProfileApiModel;

final public class UserProfileFixtures {

        private UserProfileFixtures() {
                // For Code Coverage
        }

        public static final UserId VALID_USER_ID = UserId.valueOf("de4310e5-b139-441a-99db-77c9c4a5fada");
        public static final UserId NON_EXISTING_USER_ID = UserId.valueOf("non-existing-user-id");
        public static final UserId INVALID_USER_ID = UserId.valueOf("invalid-user-id-%");

        public static final Instant LAST_UPDATE_TIMESTAMP = Instant.parse("2021-06-01T09:16:36.123Z");

        public static final UserProfilePropertyName PROPERTY_NAME_MISC_1 = UserProfilePropertyName
                        .valueOf("averagePlayTimePerWeek");

        public static final UserProfilePropertyValue PROPERTY_MISC_VALUE_1 = UserProfilePropertyValue
                        .valueOf("17.63 Hours");

        public static final UserProfilePropertyName PROPERTY_NAME_MISC_2 = UserProfilePropertyName
                        .valueOf("numbersOfLogin");

        public static final UserProfilePropertyValue PROPERTY_MISC_VALUE_2 = UserProfilePropertyValue
                        .valueOf(123);

        public static final UserProfile USER_PROFILE = new UserProfile(VALID_USER_ID, LAST_UPDATE_TIMESTAMP,
                        Map.of(PROPERTY_NAME_MISC_1, PROPERTY_MISC_VALUE_1));

        public static final UserProfileApiModel USER_PROFILE_API_EMPTY = new UserProfileApiModel(
                        VALID_USER_ID, null, null);

        public static final UserProfileApiModel USER_PROFILE_API = new UserProfileApiModel(
                        VALID_USER_ID, "replace",
                        Map.of(PROPERTY_NAME_MISC_1, PROPERTY_MISC_VALUE_1));

        public static final String SERIALIZED_USER_PROFILE = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfile.json");

}
