package com.nbp.exchangerate;

import com.nbp.client.NBPApiClient;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Data
@Component
public class ExchangeRatesValidator {

    private List<String> currencyCodesForAverageExchangeRates;
    private List<String> currencyCodesForBuyAndSellRates;
    private final NBPApiClient nbpApiClient;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TABLE_FOR_EXCHANGE_RATE = "A";
    public static final String TABLE_FOR_BUY_AND_SELL = "C";

    @Autowired
    public ExchangeRatesValidator(NBPApiClient nbpApiClient) {
        this.nbpApiClient = nbpApiClient;
    }

    @PostConstruct
    private void init() {
        currencyCodesForAverageExchangeRates = nbpApiClient.getCodesFromTables(TABLE_FOR_EXCHANGE_RATE);
        currencyCodesForBuyAndSellRates = nbpApiClient.getCodesFromTables(TABLE_FOR_BUY_AND_SELL);
    }

    public boolean isNumberOfLastQuotationsValid(String numberOfLastQuotations) {
        try {
            int value = Integer.parseInt(numberOfLastQuotations);
            return value <= 255;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCodeValid(String table, String code) {
        List<String> codes = TABLE_FOR_EXCHANGE_RATE.equals(table) ? currencyCodesForAverageExchangeRates : currencyCodesForBuyAndSellRates;
        return codes.contains(code);
    }

    public boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
