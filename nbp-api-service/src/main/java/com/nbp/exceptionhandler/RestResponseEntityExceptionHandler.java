package com.nbp.exceptionhandler;

import com.nbp.exceptionhandler.exceptions.ExchangeRateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ExchangeRateException.class})
    protected ResponseEntity<String> handleInternalServerStatus(RuntimeException exception) {
        HttpStatus intervalServerStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("Returning {} because exception was thrown", intervalServerStatus, exception);
        return new ResponseEntity<>(
                exception.getMessage(),
                intervalServerStatus);
    }
}
