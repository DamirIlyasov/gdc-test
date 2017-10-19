package com.ilyasov.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyasov.dto.CityDto;
import com.ilyasov.dto.WeatherDto;
import com.ilyasov.service.OpenWeatherMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ilyasov.damir
 * @version 1.0
 */

@Controller
public class MainController {
    private List<CityDto> cities;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    /**
     * <p>Initializing variables</p>
     * Can close the program if json with list of cities not found
     */
    @PostConstruct
    public void initializeVariables() {
        try {
            // Скачал json файл со списком городов. Если бы это был рабочий проект, то список скачивался бы при запуске приложения
            // но он весит 25 мб , поэтому, для упрощения проверки с вашей стороны, скачал его сразу
            this.cities = new ObjectMapper().readValue(new File("src/main/resources/city.list.json"), new TypeReference<List<CityDto>>() {
            });
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            System.exit(1);
        }
        cities = cities.stream().filter(city -> city.getCountry().equals("RU")).collect(Collectors.toList());
        cities.sort(Comparator.comparing(CityDto::getName));
    }

    /**
     * <p>Get index page</p>
     *
     * @return index page
     */
    @RequestMapping(value = "/")
    public String getIndexPage(Model model) throws IOException {
        model.addAttribute("cities", cities);
        return "index";
    }


    /**
     * <p>Get current weather by city id</p>
     *
     * @param cityIdString - ID city id with spaces
     * @return temperature in Celsius, two decimal places
     */
    @ResponseBody
    @RequestMapping(value = "/weather")
    public String getWeatherTemperature(@RequestParam("cityId") String cityIdString) {
        LOGGER.info("New request, processing...");
        cityIdString = cityIdString.replaceAll("\\u00A0", "");
        Long cityId = Long.valueOf(cityIdString);
        WeatherDto weatherDto = null;
        try {
            weatherDto = openWeatherMapService.getCurrentWeatherByCityId(cityId);
        } catch (IOException | RestClientException e) {
            LOGGER.warn(e.getMessage());
            return "";
        }
        double temperature = Double.parseDouble(weatherDto.getMain().get("temp"));
        temperature -= 272.15;
        LOGGER.info("Processing completed!");
        return String.format("%.2f", temperature);
    }
}
