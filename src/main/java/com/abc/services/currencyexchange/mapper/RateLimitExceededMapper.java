package com.abc.services.currencyexchange.mapper;

import com.abc.services.currencyexchange.business.impl.CurrencyExchangeServiceImpl.RateLimitExceededException;
import com.abc.services.currencyexchange.dto.response.RateLimitError;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RateLimitExceededMapper implements ExceptionMapper<RateLimitExceededException> {
    @Override
    public Response toResponse(RateLimitExceededException ex) {
        return Response.status(429)
                .entity(RateLimitError.of(ex.dni, ex.used, ex.limit, ex.resetIso))
                .build();
    }
}