package com.nbp.exchangerate;

import com.nbp.dto.MaxMinExchangeRateDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rate")
@OpenAPIDefinition(
        info = @Info(
                title = "NBP Exchange Rates API",
                version = "1.0",
                description = "API for NBP exchange rates"
        )
)
public class ExchangeRatesController {

    private final ExchangeRatesService nbpExchangeRatesService;

    public ExchangeRatesController(ExchangeRatesService nbpExchangeRatesService) {
        this.nbpExchangeRatesService = nbpExchangeRatesService;
    }

    /**
     * Retrieves the average exchange rate for a given currency code and date
     *
     * @param currencyCode The currency code to retrieve the exchange rate
     * @param date The date in yyyy-MM-dd format to retrieve the exchange rate
     * @return A ResponseEntity containing the average exchange rate for the specified currency and date
     */
    @Operation(description = "Returns average exchange rate")
    @GetMapping("/{currencyCode}/{date}")
    public ResponseEntity<Double> getAverageExchangeRateByDate(
            @Parameter(description = "Currency code") @PathVariable String currencyCode,
            @Parameter(description = "Date") @PathVariable String date
    ) {
        return new ResponseEntity<>(
                nbpExchangeRatesService.getAverageExchangeRateByDate(currencyCode, date),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves the minimum and maximum average exchange rate for a given currency code for last N quotations
     *
     * @param currencyCode The currency code to retrieve the exchange rate
     * @param numberOfLastQuotations The number of last quotations (N <= 255)
     * @return A ResponseEntity containing the maximum and minimum average rate for the last N quotations
     */
    @Operation(description = "Returns the maximum and minimum average exchange rate for the last N quotations")
    @GetMapping("/{currencyCode}/max-min-average-value")
    public ResponseEntity<MaxMinExchangeRateDTO> getMaxMinAverageExchangeRate(
            @Parameter(description = "Currency code") @PathVariable String currencyCode,
            @Parameter(description = "Number of last quotations") @RequestParam(name="numberOfLastQuotations") String numberOfLastQuotations
    ) {
        return new ResponseEntity<>(
                nbpExchangeRatesService.getMaxMinAverageExchangeRate(currencyCode, numberOfLastQuotations),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves the major difference between the buy and ask rate for a given currency code for last N quotations
     *
     * @param currencyCode The currency code to retrieve the exchange rate
     * @param numberOfLastQuotation The number of last quotations (N <= 255)
     * @return A ResponseEntity containing the major difference between the buy and ask rate
     */
    @Operation(description = "Returns major difference between the buy and ask rate")
    @GetMapping("/{currencyCode}/major-rate-difference")
    public ResponseEntity<Double> getMajorDifference(
            @Parameter(description = "Currency code") @PathVariable String currencyCode,
            @Parameter(description = "Number of last quotations") @RequestParam(name="numberOfLastQuotations") String numberOfLastQuotation
    ) {
        return new ResponseEntity<>(
                nbpExchangeRatesService.getMajorDifference(currencyCode, numberOfLastQuotation),
                HttpStatus.OK
        );
    }

    /**
     * Returns currencies codes
     *
     * @param  table The table code A or C
     * @return A list of currencies codes
     */
    @Operation(description = "Returns currencies codes")
    @GetMapping("/{table}/codes")
    public ResponseEntity<List<String>> getCurrenciesCodes(
            @Parameter(description = "Table") @PathVariable String table
    ) {
        return new ResponseEntity<>(
                nbpExchangeRatesService.getCurrenciesCodes(table),
                HttpStatus.OK
        );
    }
}
