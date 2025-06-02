package com.demo.currencyexchange.domain.currencyconversion.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyConvertResponseDTO {
    private boolean success;
    private String terms;
    private String privacy;
    private Query query;
    private Info info;
    private double result;
    private ErrorCode error;

    @Getter
    @Setter
    static class ErrorCode {
        private int code;
    }

    @Getter
    @Setter
    static class Query {
        private String from;
        private String to;
        private double amount;
    }

    @Getter
    @Setter
    static class Info {
        private long timestamp;
        private double quote;
    }

}

