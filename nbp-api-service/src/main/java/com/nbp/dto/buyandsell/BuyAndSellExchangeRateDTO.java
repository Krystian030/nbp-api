package com.nbp.dto.buyandsell;

import lombok.Data;

import java.util.List;

@Data
public class BuyAndSellExchangeRateDTO {

    private String table;
    private String currency;
    private String code;
    private List<BuyAndSellRateDTO> rates;
}
