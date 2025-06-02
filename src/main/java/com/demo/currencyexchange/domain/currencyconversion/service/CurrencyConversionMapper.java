package com.demo.currencyexchange.domain.currencyconversion.service;

import com.currencyexchange.api.model.CurrencyConversionMinDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CurrencyConversionMapper {

    @Mapping(target = "id", source = "externalId")
    @Mapping(target = "amount", source = "convertedAmount")
    CurrencyConversionMinDTO toCurrencyConversionMinDTO(String externalId, Double convertedAmount);


}
