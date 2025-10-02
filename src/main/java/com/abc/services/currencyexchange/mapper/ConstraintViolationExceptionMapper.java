package com.abc.services.currencyexchange.mapper;

import com.abc.services.currencyexchange.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException ex) {
        // Mostramos el primer mensaje legible
        String msg = ex.getConstraintViolations().stream()
                .findFirst().map(ConstraintViolation::getMessage).orElse("Validation error");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponse.of("Bad Request", msg))
                .build();
    }
}