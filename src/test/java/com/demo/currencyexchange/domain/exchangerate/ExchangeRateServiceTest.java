package com.demo.currencyexchange.domain.exchangerate;

import com.currencyexchange.api.model.ExchangeRateResponseDTO;
import com.demo.currencyexchange.config.properties.CurrencylayerProperties;
import com.demo.currencyexchange.domain.common.exception.CurrencylayerException;
import com.demo.currencyexchange.domain.exchangerate.service.ExchangeRateService;
import jakarta.validation.constraints.NotNull;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {
        CurrencylayerProperties.class})
class ExchangeRateServiceTest {

    public static final String EUR_CURRENCY = "EUR";
    public static final String USD_CURRENCY = "USD";

    public static final Double EUR_USD_RATE = 0.511436;

    @Mock
    private CurrencylayerProperties currencylayerProperties;
    private static MockWebServer mockWebServer;
    private WebClient mockedWebClient;
    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setup() {
        mockWebServer = new MockWebServer();
        mockedWebClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();
        exchangeRateService = new ExchangeRateService(currencylayerProperties, mockedWebClient);
    }

    @Test
    void getExchangeRateSuccess() {
        when(currencylayerProperties.getAccessKey()).thenReturn("adsf");
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(HttpStatus.OK.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(getSuccessMockedResponse())
        );
        String exchangeRate = exchangeRateService.getExchangeRate("EUR", "USD");
        String expectedResult = String.format("%s%s:%.6f", EUR_CURRENCY, USD_CURRENCY, EUR_USD_RATE);
        assertEquals(expectedResult, exchangeRate);
    }

    @Test
    void getExchangeRateWrongSourceCurrency() {
        when(currencylayerProperties.getAccessKey()).thenReturn("adsf");
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(201)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(get201ErrorMockedResponse())
        );
        assertThrows(CurrencylayerException.class,
                () -> exchangeRateService.getExchangeRate("EUf", "USD"));
    }

    @NotNull
    private static String getSuccessMockedResponse() {
        return "" "
        {
            "success":true,
                "timestamp":1748805617,
                "source":"BGN",
                "quotes":{
            "EURUSD":0.511436
        }
        }
        "" ";
    }

    @NotNull
    private static String get201ErrorMockedResponse() {
        return "" "
        {
            "success":false,
                "error":{
            "code":201
        }
        }
        "" ";
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.close();
    }

    private ExchangeRateResponseDTO createEURUSDResponseDTO() {
        ExchangeRateResponseDTO responseDTO = new ExchangeRateResponseDTO();
        responseDTO.setSource(EUR_CURRENCY);
        responseDTO.getQuotes().put(EUR_CURRENCY + USD_CURRENCY, 0.511436);
        responseDTO.success(true);
        return responseDTO;
    }

}
