package com.demo.currencyexchange.domain.common.exception;

import com.currencyexchange.api.model.ErrorDTO;
import com.currencyexchange.api.model.ErrorType;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class RestControllerAdvise {

    @ResponseBody
    @ExceptionHandler(CurrencylayerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequestException(CurrencylayerException ex) {
        log.error("Bad request to https://api.currencylayer.com", ex);
        String detail = "";
        switch (ex.getStatusCode()) {
            case 201:
                detail = "User entered an invalid source Currency.";
                break;
            case 202:
                detail = "User entered one or more invalid currency codes.";
                break;
            case 401:
                detail = "User entered an invalid \"source\" property.";
                break;
            case 402:
                detail = "User entered an invalid \"target\" property.";
                break;
            case 403:
                detail = "User entered no or an invalid \"amount\" property.";
                break;
        }
        return createError(ErrorType.BAD_REQUEST, "Invalid request", detail);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequestException(HttpMessageNotReadableException ex) {
        log.error("Message not readable", ex);
        String detail = ex.getClass().getSimpleName() + " - " + ex.getMessage();
        return createError(ErrorType.BAD_REQUEST, "Invalid request", detail);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception ex) {
        log.error("Unhandled exception!", ex);
        String detail = ex.getClass().getSimpleName() + " - " + ex.getMessage();
        return createError(ErrorType.UNEXPECTED_ERROR, "Unexpected Error", detail);
    }

    private static ErrorDTO createError(ErrorType type, String title, String detail) {
        ErrorDTO error = new ErrorDTO();
        error.setType(type);
        error.setTitle(title);
        error.setDetail(detail);
        return error;
    }


}
