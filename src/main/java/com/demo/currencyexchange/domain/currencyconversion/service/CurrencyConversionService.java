package com.demo.currencyexchange.domain.currencyconversion.service;

import com.currencyexchange.api.model.CurrencyConversionMinDTO;
import com.demo.currencyexchange.config.properties.CurrencylayerProperties;
import com.demo.currencyexchange.domain.common.exception.CurrencylayerException;
import com.demo.currencyexchange.domain.currencyconversion.data.CurrencyConversion;
import com.demo.currencyexchange.domain.currencyconversion.data.CurrencyConversionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CurrencyConversionService {

    private final CurrencyConversionRepository currencyConversionRepository;
    private final CurrencyConversionMapper mapper;
    private final WebClient webClient;
    private final CurrencylayerProperties currencylayerProperties;


    public CurrencyConversionMinDTO convert(double amount, String sourceCurrency, String targetCurrency) {
        //to do some business validations
        CurrencyConvertResponseDTO responseDTO = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/convert")
                        .queryParam("from", sourceCurrency)
                        .queryParam("to", targetCurrency)
                        .queryParam("amount", amount)
                        .queryParam("access_key", currencylayerProperties.getAccessKey())
                        .build())
                .retrieve()
                .bodyToMono(CurrencyConvertResponseDTO.class)
                .onErrorResume(e -> Mono.error(new Exception(e.getMessage())))
                .block();

        if (responseDTO != null && responseDTO.isSuccess()) {
            CurrencyConversion conversionEntity = new CurrencyConversion();
            conversionEntity.setAmount(amount);
            conversionEntity.setExchangeRate(responseDTO.getInfo().getQuote());
            conversionEntity.setSourceCurrency(sourceCurrency);
            conversionEntity.setTargetCurrency(targetCurrency);
            UUID uuid = UUID.nameUUIDFromBytes(responseDTO.toString().getBytes());
            conversionEntity.setExternalId(uuid.toString());

            currencyConversionRepository.save(conversionEntity);

            return mapper.toCurrencyConversionMinDTO(conversionEntity.getExternalId(), responseDTO.getResult());
        } else if (responseDTO != null && !responseDTO.isSuccess()) {
            throw new CurrencylayerException(responseDTO.getError().getCode());
        }
        return null;
    }

}
