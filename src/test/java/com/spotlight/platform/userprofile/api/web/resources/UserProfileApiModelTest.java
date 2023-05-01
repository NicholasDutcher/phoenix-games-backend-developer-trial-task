package com.spotlight.platform.userprofile.api.web.resources;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import org.junit.jupiter.api.Test;

import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeCollect;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeIncrement;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixturesCommandTypeReplace;

public class UserProfileApiModelTest {

    @Test
    void serialization_replace_WorksAsExpected() {
        assertThatJson(UserProfileFixturesCommandTypeReplace.USER_PROFILE_API_REPLACE)
                .isEqualTo(UserProfileFixturesCommandTypeReplace.SERIALIZED_USER_PROFILE_REPLACE);
    }

    @Test
    void serialization_increment_WorksAsExpected() {
        assertThatJson(UserProfileFixturesCommandTypeIncrement.USER_PROFILE_API_INCREMENT)
                .isEqualTo(UserProfileFixturesCommandTypeIncrement.SERIALIZED_USER_PROFILE_INCREMENT);
    }

    @Test
    void serialization_collect_WorksAsExpected() {
        assertThatJson(UserProfileFixturesCommandTypeCollect.USER_PROFILE_API_COLLECT)
                .isEqualTo(UserProfileFixturesCommandTypeCollect.SERIALIZED_USER_PROFILE_COLLECT);
    }
}
