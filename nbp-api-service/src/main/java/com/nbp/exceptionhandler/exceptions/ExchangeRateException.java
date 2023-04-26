package com.nbp.exceptionhandler.exceptions;

public class ExchangeRateException extends RuntimeException {

    public ExchangeRateException() {
        super();
    }

    public ExchangeRateException(String message) {
        super(message);
    }
}
