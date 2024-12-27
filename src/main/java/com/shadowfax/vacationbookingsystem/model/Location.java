package com.shadowfax.vacationbookingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String value;
    private String latitude; // Latitude and Longitude
    private String longitude;
}
