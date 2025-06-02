package com.demo.currencyexchange.domain.currencyconversion.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, Long>, JpaSpecificationExecutor<CurrencyConversion> {
}
