package com.demo.currencyexchange.domain.conversionhistory.web;

import com.currencyexchange.api.ConversionHistoryApi;
import com.currencyexchange.api.model.CurrencyConversionHistoryPageDTO;
import com.demo.currencyexchange.domain.conversionhistory.service.ConversionHistoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class ConversionHistoryController implements ConversionHistoryApi {
    private final ConversionHistoryService service;

    @Override
    public ResponseEntity<CurrencyConversionHistoryPageDTO> getTransactionsHistory(@Min(1) @Valid Integer page, @Min(1) @Valid Integer pageSize, @Valid LocalDate date, @Valid String transactionId) {
        return ResponseEntity.ok(service.getConversionsHistory(date, transactionId, page - 1, pageSize));
    }
}
