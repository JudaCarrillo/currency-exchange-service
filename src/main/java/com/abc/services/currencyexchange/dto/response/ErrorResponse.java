package com.abc.services.currencyexchange.dto.response;

public class ErrorResponse {
    public String error;
    public String message;

    public static ErrorResponse of(String error, String message) {
        ErrorResponse e = new ErrorResponse();
        e.error = error;
        e.message = message;
        return e;
    }
}
