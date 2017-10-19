package com.ilyasov.service;

import com.ilyasov.dto.WeatherDto;

import java.io.IOException;
/**
 * @author ilyasov.damir
 * @version 1.0
 */
public interface OpenWeatherMapService {
    WeatherDto getCurrentWeatherByCityId(Long cityId) throws IOException;
}
