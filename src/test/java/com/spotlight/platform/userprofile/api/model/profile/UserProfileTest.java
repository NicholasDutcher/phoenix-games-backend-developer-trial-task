package com.spotlight.platform.userprofile.api.model.profile;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import org.junit.jupiter.api.Test;

import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeCollect;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeIncrement;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeReplace;

class UserProfileTest {

    @Test
    void serialization_WorksAsExpected() {
        assertThatJson(UserProfileFixtures.USER_PROFILE).isEqualTo(UserProfileFixtures.SERIALIZED_USER_PROFILE);
    }

    @Test
    void serialization_replace_WorksAsExpected() {
        assertThatJson(UserProfileFixturesCommandTypeReplace.EXPECTED_USER_PROFILE_REPLACE)
                .isEqualTo(UserProfileFixturesCommandTypeReplace.SERIALIZED_USER_PROFILE_REPLACE_RESPONSE);
    }

    @Test
    void serialization_increment_WorksAsExpected() {
        assertThatJson(UserProfileFixturesCommandTypeIncrement.EXPECTED_USER_PROFILE_INCREMENT)
                .isEqualTo(UserProfileFixturesCommandTypeIncrement.SERIALIZED_USER_PROFILE_INCREMENT_RESPONSE);
    }

    @Test
    void serialization_collect_WorksAsExpected() {
        assertThatJson(UserProfileFixturesCommandTypeCollect.EXPECTED_USER_PROFILE_COLLECT)
                .isEqualTo(UserProfileFixturesCommandTypeCollect.SERIALIZED_USER_PROFILE_COLLECT_RESPONSE);
    }
}