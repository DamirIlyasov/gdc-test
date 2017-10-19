package com.ilyasov;

import com.ilyasov.controller.MainController;
import com.ilyasov.service.OpenWeatherMapService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:application.properties")
public class GdcTest2ApplicationTests {
    @Autowired
    Environment environment;

    @Autowired
    MainController mainController;
    @Autowired
    OpenWeatherMapService openWeatherMapService;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void contextLoads() {
        Assert.assertNotNull(mainController);
        Assert.assertNotNull(openWeatherMapService);
    }

    @Test
    public void isMainPageUrlPropertyExists() {
        Assert.assertNotNull(environment.getProperty("api.main"));
    }

    @Test
    public void isCurrentWeatherUrlPropertyExists() {
        Assert.assertNotNull(environment.getProperty("api.currentWeather"));
    }

    @Test
    public void isAppIdPropertyExists() {
        Assert.assertNotNull(environment.getProperty("app.id"));
    }

    @Test
    public void isListCitiesFileExists() {
        File listCities = new File("src/main/resources/city.list.json");
        Assert.assertNotNull(listCities);
    }

    @Test
    public void isOpenWeatherMapWebsiteWorks() {
        ResponseEntity<String> response = restTemplate.getForEntity(environment.getProperty("api.main"), String.class);
        Assert.assertEquals(response.getStatusCodeValue(), 200);
    }

}
