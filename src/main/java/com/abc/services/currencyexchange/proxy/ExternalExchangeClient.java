package com.abc.services.currencyexchange.proxy;

import com.abc.services.currencyexchange.dto.response.ExternalTodayResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/tipo-cambio")
@RegisterRestClient(configKey = "com.abc.services.external.ExternalExchangeClient")
public interface ExternalExchangeClient {

    @GET
    @Path("/today.json")
    @Produces(MediaType.APPLICATION_JSON)
    ExternalTodayResponse getToday();
}
