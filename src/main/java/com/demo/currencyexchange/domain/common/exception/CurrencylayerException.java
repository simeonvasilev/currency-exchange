package com.demo.currencyexchange.domain.common.exception;

import lombok.Getter;

@Getter
public class CurrencylayerException extends RuntimeException {
    private int statusCode;

    public CurrencylayerException(int httpStatusCode) {
        super("Exception occured during invokation of https://api.currencylayer.com");
        this.statusCode = httpStatusCode;
    }
}
