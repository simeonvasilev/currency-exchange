package com.demo.currencyexchange.domain.currencyconversion.data;

import com.demo.currencyexchange.domain.common.utils.DateUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Repository
@RequiredArgsConstructor
public class CurrencyConversionSearchRepo {
    private final CurrencyConversionRepository repository;

    public Page<CurrencyConversion> searchCurrencyConversions(OffsetDateTime filterDate, String externalId, int pageNumber, int pageSize) {
        Specification<CurrencyConversion> specification = createQuery(filterDate, externalId);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(specification, pageRequest);
    }

    Specification<CurrencyConversion> createQuery(OffsetDateTime filterDate, String externalId) {
        return orderBy()
                .and(filterByCreateDate(filterDate))
                .and(filterByExternalId(externalId));
    }

    private Specification<CurrencyConversion> filterByExternalId(String externalId) {
        if (StringUtils.isEmpty(externalId)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(CurrencyConversion_.EXTERNAL_ID), externalId);
    }

    private Specification<CurrencyConversion> filterByCreateDate(OffsetDateTime filterDate) {
        if (filterDate == null) {
            return null;
        }
        ZoneOffset systemZoneOffset = DateUtils.getSystemZoneOffset();
        OffsetDateTime from = OffsetDateTime.of(filterDate.getYear(), filterDate.getMonthValue(), filterDate.getDayOfMonth(),
                0, 0, 0, 0, systemZoneOffset);

        OffsetDateTime to = OffsetDateTime.of(filterDate.getYear(), filterDate.getMonthValue(), filterDate.getDayOfMonth(),
                23, 59, 59, 59, systemZoneOffset);

        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(CurrencyConversion_.CREATE_DATE), from, to);
    }

    private Specification<CurrencyConversion> orderBy() {
        return (root, query, criteriaBuilder) -> {
            Order orderByCreateDate = criteriaBuilder.asc(root.get(CurrencyConversion_.CREATE_DATE));
            query.orderBy(orderByCreateDate);
            return criteriaBuilder.conjunction();
        };
    }
}
