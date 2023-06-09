package com.spotlight.platform.userprofile.api.web.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;

@Path("/users/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserProfileService userProfileService;

    @Inject
    public UserResource(final UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Path("{userId}/profile")
    @GET
    public UserProfile getUserProfile(@Valid @PathParam("userId") final UserId userId) {
        return userProfileService.get(userId);
    }

    @Path("update")
    @POST
    public UserProfile replaceUserProfileProperties(final UserProfileApiModel userProfile) {
        return userProfileService.createOrUpdate(userProfile);
    }

    @Path("update/batch")
    @POST
    public List<UserProfile> replaceUserProfileProperties(final Collection<UserProfileApiModel> userProfiles) {
        final var results = new HashMap<UserId, UserProfile>();
        for (final UserProfileApiModel userProfile : userProfiles) {
            results.put(userProfile.userId(), userProfileService.createOrUpdate(userProfile));
        }
        return List.copyOf(results.values());
    }

}
