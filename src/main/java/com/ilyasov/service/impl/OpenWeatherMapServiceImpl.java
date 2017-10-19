package com.ilyasov.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyasov.dto.WeatherDto;
import com.ilyasov.service.OpenWeatherMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author ilyasov.damir
 * @version 1.0
 */
@Service
@PropertySource(value = "classpath:application.properties")
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {
    private String appid;
    private String currentWeatherUrl;
    private ObjectMapper mapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Environment environment;

    /**
     * <p>Initialize variables</p>
     * Can close the program if some of properties is not found in application.properties
     */
    @PostConstruct
    void initializeVariables() {
        appid = environment.getProperty("app.id");
        currentWeatherUrl = environment.getProperty("api.currentWeather");
        if (appid == null) {
            LOGGER.error("Can't read APPID from property file!");
            System.exit(1);
        }
        if (currentWeatherUrl == null) {
            LOGGER.error("Can't read WeatherUrl from property file!");
            System.exit(1);
        }
    }

    /**
     * <p>Get current weather from api by city id</p>
     *
     * @param cityId - ID city id with spaces
     * @return WeatherDto
     */
    @Override
    public WeatherDto getCurrentWeatherByCityId(Long cityId) throws IOException, RestClientException {
        LOGGER.info("Building request to api...");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(currentWeatherUrl)
                .queryParam("id", cityId)
                .queryParam("appid", appid);

        HttpEntity entity = new HttpEntity(httpHeaders);
        LOGGER.info("Request built, sending...");

        HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);
        LOGGER.info("Sent successful! The response is received!");
        return mapper.readValue(response.getBody(), WeatherDto.class);
    }
}
