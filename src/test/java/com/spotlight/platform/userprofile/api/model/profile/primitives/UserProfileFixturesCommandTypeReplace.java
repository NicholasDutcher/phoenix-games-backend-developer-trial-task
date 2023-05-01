package com.spotlight.platform.userprofile.api.model.profile.primitives;

import java.util.Map;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.web.resources.UserProfileApiModel;

final public class UserProfileFixturesCommandTypeReplace {

        private UserProfileFixturesCommandTypeReplace() {
                // For Code Coverage
        }

        public static final UserProfilePropertyName PROPERTY_NAME_REPLACE_1 = UserProfilePropertyName
                        .valueOf("currentGold");
        public static final UserProfilePropertyName PROPERTY_NAME_REPLACE_2 = UserProfilePropertyName
                        .valueOf("currentGems");

        public static final long PROPERTY_REPLACE_1 = 500;
        public static final UserProfilePropertyValue PROPERTY_VALUE_REPLACE_1 = UserProfilePropertyValue.valueOf(
                        PROPERTY_REPLACE_1);
        public static final long PROPERTY_REPLACE_2 = 800;
        public static final UserProfilePropertyValue PROPERTY_VALUE_REPLACE_2 = UserProfilePropertyValue.valueOf(
                        PROPERTY_REPLACE_2);

        public static final UserProfile INITIAL_USER_PROFILE_REPLACE = new UserProfile(
                        UserProfileFixtures.VALID_USER_ID, UserProfileFixtures.LAST_UPDATE_TIMESTAMP,
                        Map.of(PROPERTY_NAME_REPLACE_1, UserProfilePropertyValue.valueOf(9001)));

        public static final UserProfileApiModel USER_PROFILE_API_REPLACE = new UserProfileApiModel(
                        UserProfileFixtures.VALID_USER_ID,
                        "replace",
                        Map.of(PROPERTY_NAME_REPLACE_1, PROPERTY_VALUE_REPLACE_1, PROPERTY_NAME_REPLACE_2,
                                        PROPERTY_VALUE_REPLACE_2));

        public static final UserProfile EXPECTED_USER_PROFILE_REPLACE = new UserProfile(
                        UserProfileFixtures.VALID_USER_ID,
                        UserProfileFixtures.LAST_UPDATE_TIMESTAMP,
                        Map.of(PROPERTY_NAME_REPLACE_1, PROPERTY_VALUE_REPLACE_1, PROPERTY_NAME_REPLACE_2,
                                        PROPERTY_VALUE_REPLACE_2));

        public static final String SERIALIZED_USER_PROFILE_REPLACE = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfilePostReplace.json");

        public static final String SERIALIZED_USER_PROFILE_REPLACE_RESPONSE = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfilePostReplaceResponse.json");

}
