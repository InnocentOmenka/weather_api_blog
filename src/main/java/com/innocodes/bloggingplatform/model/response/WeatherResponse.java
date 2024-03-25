package com.innocodes.bloggingplatform.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.innocodes.bloggingplatform.model.request.WeatherMain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class WeatherResponse {
    private WeatherMain main;
    private List<WeatherDescription> weather;

}
