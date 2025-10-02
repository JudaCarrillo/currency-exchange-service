package com.abc.services.currencyexchange.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelo laxo. Si el proveedor cambia llaves, no fallamos.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalTodayResponse {
    // Ejemplos posibles; ajusta a lo que devuelva eAPI
    @JsonProperty("date")
    public String date;

    // Algunas APIs devuelven { "compra": 3.72, "venta": 3.75 }
    @JsonProperty("compra")
    public Double compra;

    @JsonProperty("venta")
    public Double venta;

    // Alternativas (por si el provider usa inglés)
    @JsonProperty("buy")
    public Double buy;

    @JsonProperty("sell")
    public Double sell;

    // A veces viene anidado; añade campos si es necesario
}
