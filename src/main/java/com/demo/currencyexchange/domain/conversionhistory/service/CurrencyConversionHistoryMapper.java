package com.demo.currencyexchange.domain.conversionhistory.service;

import com.currencyexchange.api.model.CurrencyConversionDTO;
import com.currencyexchange.api.model.CurrencyConversionHistoryPageDTO;
import com.demo.currencyexchange.domain.currencyconversion.data.CurrencyConversion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface CurrencyConversionHistoryMapper {

    @Mapping(target = "id", source = "externalId")
    CurrencyConversionDTO toDTO(CurrencyConversion currencyConversion);

    List<CurrencyConversionDTO> toDTOs(List<CurrencyConversion> currencyConversionList);

    CurrencyConversionHistoryPageDTO toPageDTO(Page<CurrencyConversion> resultPage);
}
