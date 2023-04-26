package com.nbp.exchangerate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesValidatorTest {

    @InjectMocks
    private ExchangeRatesValidator exchangeRatesValidator;

    @Test
    void testIsNumberOfLastQuotationsValidWithValidInput() {
        String numberOfLastQuotations = "200";

        assertTrue(exchangeRatesValidator.isNumberOfLastQuotationsValid(numberOfLastQuotations));
    }

    @Test
    void testIsNumberOfLastQuotationsValidWithInvalidInput() {
        String numberOfLastQuotations = "invalid";
        assertFalse(exchangeRatesValidator.isNumberOfLastQuotationsValid(numberOfLastQuotations));
    }

    @Test
    void testIsCodeValidWithValidCodeForExchangeRateTable() {
        String table = ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE;
        String code = "USD";

        List<String> codes = List.of("USD", "EUR", "GBP");
        exchangeRatesValidator.setCurrencyCodesForAverageExchangeRates(codes);

        assertTrue(exchangeRatesValidator.isCodeValid(table, code));
    }

    @Test
    void testIsCodeValidWithValidCodeForBuyAndSellTable() {
        String table = ExchangeRatesValidator.TABLE_FOR_BUY_AND_SELL;
        String code = "CHF";

        List<String> codes = List.of("CHF", "JPY", "CAD");
        exchangeRatesValidator.setCurrencyCodesForBuyAndSellRates(codes);

        assertTrue(exchangeRatesValidator.isCodeValid(table, code));
    }

    @Test
    void testIsCodeValidWithInvalidCodeForExchangeRateTable() {
        String table = ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE;
        String code = "invalid";

        List<String> codes = List.of("USD", "EUR", "GBP");
        exchangeRatesValidator.setCurrencyCodesForAverageExchangeRates(codes);
        ;

        assertFalse(exchangeRatesValidator.isCodeValid(table, code));
    }

    @Test
    void testIsCodeValidWithInvalidCodeForBuyAndSellTable() {
        String table = ExchangeRatesValidator.TABLE_FOR_BUY_AND_SELL;
        String code = "invalid";

        List<String> codes = List.of("CHF", "JPY", "CAD");
        exchangeRatesValidator.setCurrencyCodesForBuyAndSellRates(codes);

        assertFalse(exchangeRatesValidator.isCodeValid(table, code));
    }

    @Test
    void testIsValidDateWithValidDate() {
        String date = "2022-04-26";
        assertTrue(exchangeRatesValidator.isValidDate(date));
    }

    @Test
    void testIsValidDateWithInvalidValueDate() {
        String date = "2022-50-26";
        assertFalse(exchangeRatesValidator.isValidDate(date));
    }

    @Test
    void testIsValidDateWithInvalidFormatDate() {
        String date = "invalid";
        assertFalse(exchangeRatesValidator.isValidDate(date));
    }
}