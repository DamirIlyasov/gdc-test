package com.ilyasov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * @author ilyasov.damir
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    private Map<String, String> main;

    public Map<String, String> getMain() {
        return main;
    }

    public void setMain(Map<String, String> main) {
        this.main = main;
    }

}
