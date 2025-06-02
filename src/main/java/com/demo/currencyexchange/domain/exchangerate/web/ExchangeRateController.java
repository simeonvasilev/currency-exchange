package com.demo.currencyexchange.domain.exchangerate.web;

import com.currencyexchange.api.ExchangeRateApi;
import com.demo.currencyexchange.domain.exchangerate.service.ExchangeRateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRateController implements ExchangeRateApi {
    private final ExchangeRateService exchangeRateService;

    @Override
    public ResponseEntity<String> getExchangeRate(@NotNull @Size(min = 3, max = 3) @Valid String currency1, @NotNull @Size(min = 3, max = 3) @Valid String currency2) {
        return ResponseEntity.ok(exchangeRateService.getExchangeRate(currency1, currency2));
    }
}
