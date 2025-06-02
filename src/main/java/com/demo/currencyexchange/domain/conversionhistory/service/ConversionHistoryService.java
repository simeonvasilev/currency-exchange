package com.demo.currencyexchange.domain.conversionhistory.service;

import com.currencyexchange.api.model.CurrencyConversionHistoryPageDTO;
import com.demo.currencyexchange.domain.currencyconversion.data.CurrencyConversion;
import com.demo.currencyexchange.domain.currencyconversion.data.CurrencyConversionSearchRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ConversionHistoryService {
    private final CurrencyConversionSearchRepo searchRepository;
    private final CurrencyConversionHistoryMapper mapper;

    public CurrencyConversionHistoryPageDTO getConversionsHistory(LocalDate dateFilter, String id, int page, int pageSize) {
        OffsetDateTime offsetDateTimeFilter = null;
        if (dateFilter != null) {
            offsetDateTimeFilter = OffsetDateTime.of(dateFilter, LocalTime.MIN, ZoneOffset.UTC);
        }
        Page<CurrencyConversion> resultPage = searchRepository.searchCurrencyConversions(offsetDateTimeFilter, id, page, pageSize);
        return mapper.toPageDTO(resultPage);
    }
}
