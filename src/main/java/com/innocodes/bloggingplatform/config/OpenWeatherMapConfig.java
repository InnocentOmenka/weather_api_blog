package com.innocodes.bloggingplatform.config;

import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration

public class OpenWeatherMapConfig {

    @Value("${openweathermap.apiKey}")
    private String apiKey;
    public String getApiKey() {
        return apiKey;
    }
}
