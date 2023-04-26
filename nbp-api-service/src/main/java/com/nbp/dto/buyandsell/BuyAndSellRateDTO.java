package com.nbp.dto.buyandsell;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BuyAndSellRateDTO {

    private String no;
    private LocalDate effectiveDate;
    private Double bid;
    private Double ask;
}
