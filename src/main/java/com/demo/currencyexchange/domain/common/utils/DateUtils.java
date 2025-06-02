package com.demo.currencyexchange.domain.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Component
public class DateUtils {
    private static ZoneId systemDefaultZoneId = ZoneId.systemDefault();

    public static ZoneOffset getSystemZoneOffset() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return systemDefaultZoneId.getRules().getOffset(localDateTime);
    }
}
