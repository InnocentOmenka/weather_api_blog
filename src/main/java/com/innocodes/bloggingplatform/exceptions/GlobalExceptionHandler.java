package com.innocodes.bloggingplatform.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {

    private static final Logger loggerUtil = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(WeatherServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<String> handleBadRequestException(WeatherServiceException exception) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        errorResponse.setStatus("ERROR");
        errorResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST));
        errorResponse.setMessage(exception.getMessage());
        loggerUtil.error(errorResponse.getMessage(), exception);
        return errorResponse;
    }
}
