package com.nbp.dto.table;

import lombok.Data;

import java.util.List;

@Data
public class CurrentCurrencyDTO {

    private String table;
    private List<TableRateDTO> rates;
}
