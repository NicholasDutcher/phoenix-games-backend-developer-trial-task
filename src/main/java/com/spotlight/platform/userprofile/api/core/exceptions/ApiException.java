package com.spotlight.platform.userprofile.api.core.exceptions;

import javax.ws.rs.core.Response.Status;

public class ApiException extends RuntimeException {

    private final Status responseStatus;

    public ApiException(final String message, final Status responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public Status getResponseStatus() {
        return responseStatus;
    }
}