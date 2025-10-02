package com.abc.services.currencyexchange.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyExchangeResponse {
    public String date;      // "2025-10-01"
    public String currency;  // "USD"
    public double buyRate;   // compra
    public double sellRate;  // venta
    public String source;    // "SUNAT/SBS"
}
