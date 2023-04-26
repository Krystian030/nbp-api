package com.nbp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MaxMinExchangeRateDTO {

    private Double maximumExchangeRate;
    private Double minimumExchangeRate;
}
