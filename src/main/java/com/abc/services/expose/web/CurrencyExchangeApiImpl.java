package com.abc.services.expose.web;

import com.abc.services.currencyexchange.business.CurrencyExchangeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@ApplicationScoped
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyExchangeApiImpl {

    private static final Logger Log = Logger.getLogger(CurrencyExchangeApiImpl.class);

    private final CurrencyExchangeService service;

    @Inject
    public CurrencyExchangeApiImpl(CurrencyExchangeService service) {
        this.service = service;
    }

    @GET
    @Path("/currency-exchange")
    public Response retrieveCurrencyExchange(
            @QueryParam("dni")
            @NotBlank(message = "DNI parameter is required")
            @Pattern(regexp = "\\d{8}", message = "DNI must be 8 digits")
            String dni) {

        Log.infof("Received exchange rate request dni=%s", dni);

        var rl = service.getCurrencyExchange(dni);
        int remaining = Math.max(0, rl.limit() - rl.usedPost());
        return Response.ok(rl.body())
                .header("X-RateLimit-Limit", rl.limit())
                .header("X-RateLimit-Remaining", remaining)
                .header("X-RateLimit-Reset", rl.resetIso())
                .build();
    }
}
