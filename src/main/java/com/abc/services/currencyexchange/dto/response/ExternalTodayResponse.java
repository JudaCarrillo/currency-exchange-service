package com.abc.services.currencyexchange.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalTodayResponse {
    @JsonProperty("date")
    public String date;

    @JsonProperty("compra")
    public Double compra;

    @JsonProperty("venta")
    public Double venta;

    @JsonProperty("buy")
    public Double buy;

    @JsonProperty("sell")
    public Double sell;

}
