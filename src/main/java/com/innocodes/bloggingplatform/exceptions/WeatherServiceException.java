package com.innocodes.bloggingplatform.exceptions;

public class WeatherServiceException extends RuntimeException{
    public WeatherServiceException(String message) {
        super(message);
    }
}
