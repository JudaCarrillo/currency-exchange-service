package com.abc.services.currencyexchange.mapper;

import com.abc.services.currencyexchange.business.impl.CurrencyExchangeServiceImpl.UpstreamUnavailableException;
import com.abc.services.currencyexchange.dto.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UpstreamUnavailableMapper implements ExceptionMapper<UpstreamUnavailableException> {
    @Override
    public Response toResponse(UpstreamUnavailableException ex) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(ErrorResponse.of("Service Unavailable",
                        "External exchange rate service is temporarily unavailable. Please try again later."))
                .build();
    }
}
