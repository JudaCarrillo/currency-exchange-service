package com.abc.services.currencyexchange.business;

import com.abc.services.currencyexchange.dto.response.CurrencyExchangeResponse;
import com.abc.services.currencyexchange.dto.response.RateLimited;

public interface CurrencyExchangeService {
    RateLimited<CurrencyExchangeResponse> getCurrencyExchange(String dni);
}
