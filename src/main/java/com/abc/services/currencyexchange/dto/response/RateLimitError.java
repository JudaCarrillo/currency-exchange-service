package com.abc.services.currencyexchange.dto.response;

public class RateLimitError {
    public String error;
    public String message;
    public String dni;
    public int queries_used;
    public int queries_limit;
    public String reset_time;

    public static RateLimitError of(String dni, int used, int limit, String resetIso) {
        RateLimitError r = new RateLimitError();
        r.error = "Rate Limit Exceeded";
        r.message = "Daily consultation limit reached. Maximum " + limit + " queries per day.";
        r.dni = dni;
        r.queries_used = used;
        r.queries_limit = limit;
        r.reset_time = resetIso;
        return r;
    }
}