package com.spotlight.platform.userprofile.api.model.profile.primitives;

import java.util.Map;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.web.resources.UserProfileApiModel;

final public class UserProfileFixturesCommandTypeIncrement {

        private UserProfileFixturesCommandTypeIncrement() {
                // For Code Coverage
        }

        public static final UserProfilePropertyName PROPERTY_NAME_INCREMENT_1 = UserProfilePropertyName
                        .valueOf("battleFought");
        public static final UserProfilePropertyName PROPERTY_NAME_INCREMENT_2 = UserProfilePropertyName
                        .valueOf("questsNotCompleted");

        public static final long PROPERTY_INCREMENT_1 = 10;
        public static final UserProfilePropertyValue PROPERTY_VALUE_INCREMENT_1 = UserProfilePropertyValue.valueOf(
                        PROPERTY_INCREMENT_1);
        public static final long PROPERTY_INCREMENT_2 = -1;
        public static final UserProfilePropertyValue PROPERTY_VALUE_INCREMENT_2 = UserProfilePropertyValue.valueOf(
                        PROPERTY_INCREMENT_2);

        public static final UserProfile INITIAL_USER_PROFILE_INCREMENT = new UserProfile(
                        UserProfileFixtures.VALID_USER_ID, UserProfileFixtures.LAST_UPDATE_TIMESTAMP,
                        Map.of(PROPERTY_NAME_INCREMENT_2, UserProfilePropertyValue.valueOf(0)));

        public static final UserProfileApiModel USER_PROFILE_API_INCREMENT = new UserProfileApiModel(
                        UserProfileFixtures.VALID_USER_ID,
                        "increment",
                        Map.of(PROPERTY_NAME_INCREMENT_1, PROPERTY_VALUE_INCREMENT_1, PROPERTY_NAME_INCREMENT_2,
                                        PROPERTY_VALUE_INCREMENT_2));

        public static final UserProfile EXPECTED_USER_PROFILE_INCREMENT = new UserProfile(
                        UserProfileFixtures.VALID_USER_ID,
                        UserProfileFixtures.LAST_UPDATE_TIMESTAMP, Map.of(PROPERTY_NAME_INCREMENT_1,
                                        UserProfilePropertyValue.valueOf(PROPERTY_INCREMENT_1),
                                        PROPERTY_NAME_INCREMENT_2,
                                        UserProfilePropertyValue.valueOf(PROPERTY_INCREMENT_2)));

        public static final String SERIALIZED_USER_PROFILE_INCREMENT = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfilePostIncrement.json");

        public static final String SERIALIZED_USER_PROFILE_INCREMENT_RESPONSE = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfilePostIncrementResponse.json");

}
