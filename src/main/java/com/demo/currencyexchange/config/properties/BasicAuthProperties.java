package com.demo.currencyexchange.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "currencyexchange.basic-auth")
@Getter
@Setter
public class BasicAuthProperties {

    private String reader;

    private String admin;
}
