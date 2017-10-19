package com.ilyasov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ilyasov.damir
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityDto {
    private Long id;
    private String name;
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
