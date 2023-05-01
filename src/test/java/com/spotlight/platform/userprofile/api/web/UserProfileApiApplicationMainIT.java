package com.spotlight.platform.userprofile.api.web;

import org.junit.jupiter.api.Test;

class UserProfileApiApplicationMainIT {

    @Test
    void test_main() {
        try {
            UserProfileApiApplication.main(new String[] {});
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}