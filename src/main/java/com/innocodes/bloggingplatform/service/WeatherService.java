package com.innocodes.bloggingplatform.service;

import com.innocodes.bloggingplatform.config.OpenWeatherMapConfig;
import com.innocodes.bloggingplatform.exceptions.WeatherServiceException;
import com.innocodes.bloggingplatform.model.response.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final OpenWeatherMapConfig openWeatherMapConfig;

    public WeatherResponse getWeatherData(String location) {
        String apiKey = openWeatherMapConfig.getApiKey();
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&units=metric&appid=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(apiUrl, WeatherResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new WeatherServiceException("Failed to fetch weather data from OpenWeatherMap API");
        }
    }
}
