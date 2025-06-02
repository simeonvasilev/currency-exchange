package com.demo.currencyexchange.domain.currencyconversion.web;

import com.currencyexchange.api.CurrencyConversionApi;
import com.currencyexchange.api.model.CurrencyConversionMinDTO;
import com.demo.currencyexchange.domain.currencyconversion.service.CurrencyConversionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class CurrencyConversionController implements CurrencyConversionApi {
    private final CurrencyConversionService service;

    @Override
    public ResponseEntity<CurrencyConversionMinDTO> convertCurrency(@NotNull @Valid Double amount, @NotNull @Size(min = 3, max = 3) @Valid String sourceCurrency, @NotNull @Size(min = 3, max = 3) @Valid String targetCurrency) {
        return ResponseEntity.ok(service.convert(amount, sourceCurrency, targetCurrency));
    }
}
