package com.abc.services.currencyexchange.dto.response;

public record RateLimited<T>(T body, int limit, int usedPost, String resetIso) {}
