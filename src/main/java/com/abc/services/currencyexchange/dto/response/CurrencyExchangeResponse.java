package com.abc.services.currencyexchange.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyExchangeResponse {
    public String date;
    public String currency;
    public double buyRate;
    public double sellRate;
    public String source;
}
