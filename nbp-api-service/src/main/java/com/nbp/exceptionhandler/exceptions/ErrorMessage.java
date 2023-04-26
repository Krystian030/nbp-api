package com.nbp.exceptionhandler.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    AVERAGE_EXCHANGE_RATE_ERROR("Error while getting the average exchange rate"),
    AVERAGE_MAX_MIN_EXCHANGE_RATE_ERROR("Error while getting the max and min average exchange rate"),
    MAJOR_DIFFERENCE_EXCHANGE_RATE_ERROR("Error while getting a major difference"),
    GET_CODE_ERROR("Error while getting a codes for table {}");

    private final String message;
}
