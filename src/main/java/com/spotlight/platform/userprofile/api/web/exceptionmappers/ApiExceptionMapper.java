package com.spotlight.platform.userprofile.api.web.exceptionmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.spotlight.platform.userprofile.api.core.exceptions.ApiException;

public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse(final ApiException exception) {
        return Response.status(exception.getResponseStatus().getStatusCode(), exception.getMessage()).build();
    }
}
