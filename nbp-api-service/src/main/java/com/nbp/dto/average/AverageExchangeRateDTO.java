package com.nbp.dto.average;

import lombok.Data;

import java.util.List;

@Data
public class AverageExchangeRateDTO {

    private String table;
    private String currency;
    private String code;
    private List<AverageRateDTO> rates;
}
