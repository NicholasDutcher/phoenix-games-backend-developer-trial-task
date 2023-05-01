package com.spotlight.platform.userprofile.api.model.profile.primitives;

import java.util.List;
import java.util.Map;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.web.resources.UserProfileApiModel;

final public class UserProfileFixturesCommandTypeCollect {

        private UserProfileFixturesCommandTypeCollect() {
                // For Code Coverage
        }

        public static final UserProfilePropertyName PROPERTY_NAME_COLLECT_1 = UserProfilePropertyName
                        .valueOf("inventory");
        public static final UserProfilePropertyName PROPERTY_NAME_COLLECT_2 = UserProfilePropertyName
                        .valueOf("tools");

        public static final List<String> PROPERTY_COLLECT_0 = List.of("sword1", "sword2");
        public static final UserProfilePropertyValue PROPERTY_VALUE_COLLECT_0 = UserProfilePropertyValue
                        .valueOf(PROPERTY_COLLECT_0);

        public static final List<String> PROPERTY_COLLECT_00 = List.of("shield1");
        public static final UserProfilePropertyValue PROPERTY_VALUE_COLLECT_00 = UserProfilePropertyValue
                        .valueOf(PROPERTY_COLLECT_00);

        public static final List<String> PROPERTY_COLLECT_1 = List.of("sword1", "sword2", "shield1");
        public static final UserProfilePropertyValue PROPERTY_VALUE_COLLECT_1 = UserProfilePropertyValue
                        .valueOf(PROPERTY_COLLECT_1);
        public static final List<String> PROPERTY_COLLECT_2 = List.of("tool1", "tool2");
        public static final UserProfilePropertyValue PROPERTY_VALUE_COLLECT_2 = UserProfilePropertyValue
                        .valueOf(PROPERTY_COLLECT_2);

        public static final UserProfile INITIAL_USER_PROFILE_COLLECT = new UserProfile(
                        UserProfileFixtures.VALID_USER_ID, UserProfileFixtures.LAST_UPDATE_TIMESTAMP,
                        Map.of(PROPERTY_NAME_COLLECT_1, PROPERTY_VALUE_COLLECT_0));

        public static final UserProfileApiModel USER_PROFILE_API_COLLECT = new UserProfileApiModel(
                        UserProfileFixtures.VALID_USER_ID,
                        "collect", Map.of(PROPERTY_NAME_COLLECT_1, PROPERTY_VALUE_COLLECT_00, PROPERTY_NAME_COLLECT_2,
                                        PROPERTY_VALUE_COLLECT_2));

        public static final UserProfile EXPECTED_USER_PROFILE_COLLECT = new UserProfile(
                        UserProfileFixtures.VALID_USER_ID,
                        UserProfileFixtures.LAST_UPDATE_TIMESTAMP,
                        Map.of(PROPERTY_NAME_COLLECT_1, PROPERTY_VALUE_COLLECT_1, PROPERTY_NAME_COLLECT_2,
                                        PROPERTY_VALUE_COLLECT_2));

        public static final String SERIALIZED_USER_PROFILE_COLLECT = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfilePostCollect.json");

        public static final String SERIALIZED_USER_PROFILE_COLLECT_RESPONSE = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfilePostCollectResponse.json");

}
