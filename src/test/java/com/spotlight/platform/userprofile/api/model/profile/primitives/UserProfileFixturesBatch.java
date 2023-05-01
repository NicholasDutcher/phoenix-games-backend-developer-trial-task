package com.spotlight.platform.userprofile.api.model.profile.primitives;

import java.util.Map;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;

final public class UserProfileFixturesBatch {

        private UserProfileFixturesBatch() {
                // For Code Coverage
        }

        public static final UserProfile USER_PROFILE_MOCK = new UserProfile(
                        UserProfileFixtures.VALID_USER_ID, UserProfileFixtures.LAST_UPDATE_TIMESTAMP, Map.of(
                                        UserProfileFixturesCommandTypeCollect.PROPERTY_NAME_COLLECT_1,
                                        UserProfileFixturesCommandTypeCollect.PROPERTY_VALUE_COLLECT_0,
                                        UserProfileFixturesCommandTypeReplace.PROPERTY_NAME_REPLACE_1,
                                        UserProfilePropertyValue.valueOf(550),
                                        UserProfileFixturesCommandTypeReplace.PROPERTY_NAME_REPLACE_2,
                                        UserProfilePropertyValue.valueOf(880),
                                        UserProfileFixturesCommandTypeIncrement.PROPERTY_NAME_INCREMENT_1,
                                        UserProfileFixturesCommandTypeIncrement.PROPERTY_VALUE_INCREMENT_1,
                                        UserProfileFixturesCommandTypeIncrement.PROPERTY_NAME_INCREMENT_2,
                                        UserProfileFixturesCommandTypeIncrement.PROPERTY_VALUE_INCREMENT_2));

        public static final String SERIALIZED_USER_PROFILE_BATCH = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfilePostBatch.json");

        public static final String SERIALIZED_USER_PROFILE_BATCH_RESPONSE = FixtureHelpers
                        .fixture("/fixtures/model/profile/userProfilePostBatchResponse.json");

}
