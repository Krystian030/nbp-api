package com.nbp.client;

import com.nbp.dto.MaxMinExchangeRateDTO;
import com.nbp.dto.average.AverageExchangeRateDTO;
import com.nbp.dto.average.AverageRateDTO;
import com.nbp.dto.buyandsell.BuyAndSellExchangeRateDTO;
import com.nbp.dto.table.CurrentCurrencyDTO;
import com.nbp.dto.table.TableRateDTO;
import com.nbp.exceptionhandler.exceptions.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NBPApiClient {

    private final WebClient nbpClient;

    @Autowired
    public NBPApiClient(WebClient nbpClient) {
        this.nbpClient = nbpClient;
    }

    public Optional<Double> getAverageExchangeRateByDate(String table, String currencyCode, String date) {
        String url = String.format("exchangerates/rates/%s/%s/%s", table, currencyCode, date);
        try {
            Optional<AverageExchangeRateDTO> response = nbpClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(AverageExchangeRateDTO.class)
                    .blockOptional();
            if (response.isEmpty() || CollectionUtils.isEmpty(response.get().getRates())) {
                return Optional.empty();
            }

            return Optional.of(response.get()
                    .getRates()
                    .stream()
                    .mapToDouble(AverageRateDTO::getMid)
                    .average()
                    .orElseThrow()
            );
        } catch (Exception e) {
            log.error(ErrorMessage.AVERAGE_EXCHANGE_RATE_ERROR.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<MaxMinExchangeRateDTO> getMaxMinAverageExchangeRate(String table, String currencyCode, String topCount) {
        String url = String.format("exchangerates/rates/%s/%s/last/%s", table, currencyCode, topCount);
        try {
            Optional<AverageExchangeRateDTO> response = nbpClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(AverageExchangeRateDTO.class)
                    .blockOptional();
            if (response.isEmpty() || CollectionUtils.isEmpty(response.get().getRates())) {
                return Optional.empty();
            }

            List<Double> midRates = response.get().getRates()
                    .stream()
                    .map(AverageRateDTO::getMid)
                    .toList();
            return Optional.of(MaxMinExchangeRateDTO.builder()
                    .minimumExchangeRate(Collections.min(midRates))
                    .maximumExchangeRate(Collections.max(midRates))
                    .build()
            );
        } catch (Exception e) {
            log.error(ErrorMessage.AVERAGE_MAX_MIN_EXCHANGE_RATE_ERROR.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<Double> getMajorDifference(String table, String currencyCode, String topCount) {
        String url = String.format("exchangerates/rates/%s/%s/last/%s", table, currencyCode, topCount);
        try {
            Optional<BuyAndSellExchangeRateDTO> response = nbpClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(BuyAndSellExchangeRateDTO.class)
                    .blockOptional();
            if (response.isEmpty() || CollectionUtils.isEmpty(response.get().getRates())) {
                return Optional.empty();
            }

            return Optional.of(
                    response.get().getRates()
                            .stream()
                            .mapToDouble(rate -> Math.abs(rate.getAsk() - rate.getBid()))
                            .max()
                            .orElseThrow()
            );

        } catch (Exception e) {
            log.error(ErrorMessage.MAJOR_DIFFERENCE_EXCHANGE_RATE_ERROR.getMessage(), e);
            return Optional.empty();
        }
    }

    public List<String> getCodesFromTables(String table) {
        String url = String.format("exchangerates/tables/%s", table);
        try {
            Optional<List<CurrentCurrencyDTO>> response = nbpClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<CurrentCurrencyDTO>>() {})
                    .blockOptional();
            if (response.isEmpty() || CollectionUtils.isEmpty(response.get().get(0).getRates())) {
                return new ArrayList<>();
            }

            return response.get().get(0).getRates()
                    .stream()
                    .map(TableRateDTO::getCode)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(ErrorMessage.GET_CODE_ERROR.getMessage(), table, e);
            return new ArrayList<>();
        }
    }
}
