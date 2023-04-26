package com.nbp.exchangerate;

import com.nbp.client.NBPApiClient;
import com.nbp.dto.MaxMinExchangeRateDTO;
import com.nbp.exceptionhandler.exceptions.ErrorMessage;
import com.nbp.exceptionhandler.exceptions.ExchangeRateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesServiceTest {

    @InjectMocks
    private ExchangeRatesService exchangeRatesService;

    @Mock
    private NBPApiClient client;

    @Mock
    private ExchangeRatesValidator validator;

    @Test
    void testGetAverageExchangeRateByDateWithValidInputs() {
        String currencyCode = "USD";
        String date = "2021-09-01";
        Double expectedExchangeRate = 3.8;

        when(validator.isValidDate(date)).thenReturn(true);
        when(validator.isCodeValid(ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE, currencyCode)).thenReturn(true);
        when(client.getAverageExchangeRateByDate(ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE, currencyCode, date)).thenReturn(Optional.of(expectedExchangeRate));

        Double actualExchangeRate = exchangeRatesService.getAverageExchangeRateByDate(currencyCode, date);
        assertEquals(expectedExchangeRate, actualExchangeRate);
    }

    @Test
    void testGetAverageExchangeRateByDateWithInvalidDate() {
        String currencyCode = "USD";
        String date = "invalid date";

        when(validator.isValidDate(date)).thenReturn(false);

        ExchangeRateException exchangeRateException = assertThrows(ExchangeRateException.class, () -> exchangeRatesService.getAverageExchangeRateByDate(currencyCode, date));
        assertEquals(ErrorMessage.AVERAGE_EXCHANGE_RATE_ERROR.getMessage(), exchangeRateException.getMessage());
    }

    @Test
    void testGetAverageExchangeRateByDateWithInvalidCode() {
        String currencyCode = "invalid code";
        String date = "2021-09-01";

        when(validator.isValidDate(date)).thenReturn(true);
        when(validator.isCodeValid(ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE, currencyCode)).thenReturn(false);

        ExchangeRateException exchangeRateException = assertThrows(ExchangeRateException.class, () -> exchangeRatesService.getAverageExchangeRateByDate(currencyCode, date));
        assertEquals(ErrorMessage.AVERAGE_EXCHANGE_RATE_ERROR.getMessage(), exchangeRateException.getMessage());
    }

    @Test
    void testGetMaxMinAverageExchangeRateWithValidInputs() {
        String currencyCode = "USD";
        String numberOfLastQuotations = "30";
        MaxMinExchangeRateDTO expectedDto = new MaxMinExchangeRateDTO(3.5, 4.0);

        when(validator.isCodeValid(ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE, currencyCode)).thenReturn(true);
        when(validator.isNumberOfLastQuotationsValid(numberOfLastQuotations)).thenReturn(true);
        when(client.getMaxMinAverageExchangeRate(ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE, currencyCode, numberOfLastQuotations)).thenReturn(Optional.of(expectedDto));

        MaxMinExchangeRateDTO actualDto = exchangeRatesService.getMaxMinAverageExchangeRate(currencyCode, numberOfLastQuotations);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetMaxMinAverageExchangeRateWithInvalidCode() {
        String currencyCode = "invalid code";
        String numberOfLastQuotations = "30";

        when(validator.isCodeValid(ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE, currencyCode)).thenReturn(false);

        ExchangeRateException exchangeRateException = assertThrows(ExchangeRateException.class, () -> exchangeRatesService.getMaxMinAverageExchangeRate(currencyCode, numberOfLastQuotations));
        assertEquals(ErrorMessage.AVERAGE_MAX_MIN_EXCHANGE_RATE_ERROR.getMessage(), exchangeRateException.getMessage());
    }

    @Test
    void testGetMaxMinAverageExchangeRateWithInvalidNumberOfLastQuotations() {
        String currencyCode = "USD";
        String numberOfLastQuotations = "-1";

        when(validator.isCodeValid(ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE, currencyCode)).thenReturn(true);
        when(validator.isNumberOfLastQuotationsValid(numberOfLastQuotations)).thenReturn(false);

        ExchangeRateException exchangeRateException = assertThrows(ExchangeRateException.class, () -> exchangeRatesService.getMaxMinAverageExchangeRate(currencyCode, numberOfLastQuotations));
        assertEquals(ErrorMessage.AVERAGE_MAX_MIN_EXCHANGE_RATE_ERROR.getMessage(), exchangeRateException.getMessage());
    }
}