
package com.nbp.exchangerate;

import com.nbp.client.NBPApiClient;
import com.nbp.dto.MaxMinExchangeRateDTO;
import com.nbp.exceptionhandler.exceptions.ErrorMessage;
import com.nbp.exceptionhandler.exceptions.ExchangeRateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nbp.exchangerate.ExchangeRatesValidator.TABLE_FOR_BUY_AND_SELL;
import static com.nbp.exchangerate.ExchangeRatesValidator.TABLE_FOR_EXCHANGE_RATE;

@Service
public class ExchangeRatesService {

    private final NBPApiClient client;
    private final ExchangeRatesValidator validator;

    @Autowired
    public ExchangeRatesService(NBPApiClient client, ExchangeRatesValidator validator) {
        this.client = client;
        this.validator = validator;
    }

    public Double getAverageExchangeRateByDate(String currencyCode, String date) {
        if (validator.isValidDate(date) && validator.isCodeValid(TABLE_FOR_EXCHANGE_RATE, currencyCode)) {
            return client.getAverageExchangeRateByDate(TABLE_FOR_EXCHANGE_RATE, currencyCode, date)
                    .orElseThrow(ExchangeRateException::new);
        }

        throw new ExchangeRateException(ErrorMessage.AVERAGE_EXCHANGE_RATE_ERROR.getMessage());
    }

    public MaxMinExchangeRateDTO getMaxMinAverageExchangeRate(String currencyCode, String numberOfLastQuotations) {
        if (validator.isCodeValid(TABLE_FOR_EXCHANGE_RATE, currencyCode) && validator.isNumberOfLastQuotationsValid(numberOfLastQuotations)) {
            return client.getMaxMinAverageExchangeRate(TABLE_FOR_EXCHANGE_RATE, currencyCode, numberOfLastQuotations).orElseThrow(ExchangeRateException::new);
        }

        throw new ExchangeRateException(ErrorMessage.AVERAGE_MAX_MIN_EXCHANGE_RATE_ERROR.getMessage());
    }

    public Double getMajorDifference(String currencyCode, String numberOfLastQuotations) {
        if (validator.isCodeValid(TABLE_FOR_BUY_AND_SELL, currencyCode) && validator.isNumberOfLastQuotationsValid(numberOfLastQuotations)) {
            return client.getMajorDifference(TABLE_FOR_BUY_AND_SELL, currencyCode, numberOfLastQuotations).orElseThrow(ExchangeRateException::new);
        }

        throw new ExchangeRateException(ErrorMessage.MAJOR_DIFFERENCE_EXCHANGE_RATE_ERROR.getMessage());
    }

    public List<String> getCurrenciesCodes(String table) {
        return client.getCodesFromTables(table);
    }
}
