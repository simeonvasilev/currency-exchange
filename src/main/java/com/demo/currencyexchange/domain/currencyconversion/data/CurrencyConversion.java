package com.demo.currencyexchange.domain.currencyconversion.data;

import com.demo.currencyexchange.domain.common.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "currency_conversion", uniqueConstraints = @UniqueConstraint(name = "uidx_external_id", columnNames = {"external_id"}))
@Getter
@Setter
@SequenceGenerator(name = "seq_currency_conversion", sequenceName = "seq_currency_conversion")
public class CurrencyConversion {

    @Id
    @GeneratedValue(generator = "seq_currency_conversion", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "external_id", updatable = false, nullable = false)
    private String externalId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "source_currency", nullable = false)
    private String sourceCurrency;

    @Column(name = "target_currency", nullable = false)
    private String targetCurrency;

    @Column(name = "create_date")
    private OffsetDateTime createDate;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @PrePersist
    public void onInsert() {
        this.createDate = OffsetDateTime.now(DateUtils.getSystemZoneOffset());
    }
}
