package com.demo.currencyexchange.domain.exchangerate.service;

import com.currencyexchange.api.model.ExchangeRateResponseDTO;
import com.demo.currencyexchange.config.properties.CurrencylayerProperties;
import com.demo.currencyexchange.domain.common.exception.CurrencylayerException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final CurrencylayerProperties currencylayerProperties;
    private final WebClient webClient;

    @Cacheable("exchangeRate")
    public String getExchangeRate(String currency1, String currency2) {
        //to do some business validations
        ExchangeRateResponseDTO responseDTO = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/live")
                        .queryParam("source", currency1)
                        .queryParam("currencies", currency2)
                        .queryParam("access_key", currencylayerProperties.getAccessKey())
                        .build())
                .retrieve()
                .bodyToMono(ExchangeRateResponseDTO.class)
                .onErrorResume(e -> Mono.error(new Exception(e.getMessage())))
                .block();
        String response = "";
        if (responseDTO != null && responseDTO.getSuccess() && !responseDTO.getQuotes().isEmpty()) {
            String key = currency1.concat(currency2);
            response = String.format("%s:%.6f", key, responseDTO.getQuotes().get(key));
        } else if (responseDTO != null && !responseDTO.getSuccess()) {
            throw new CurrencylayerException(responseDTO.getError().getCode());
        }
        return response;
    }
}
