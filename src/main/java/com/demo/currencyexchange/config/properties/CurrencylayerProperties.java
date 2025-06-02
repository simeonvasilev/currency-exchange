package com.demo.currencyexchange.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "currencylayer.api")
@Getter
@Setter
public class CurrencylayerProperties {

    private String url;

    private String accessKey;
}
