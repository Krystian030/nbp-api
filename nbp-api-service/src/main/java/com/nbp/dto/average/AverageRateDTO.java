package com.nbp.dto.average;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AverageRateDTO {

    private String no;
    private LocalDate effectiveDate;
    private Double mid;
}
