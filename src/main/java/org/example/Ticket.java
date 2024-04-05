package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Ticket {
    private String origin;
    @JsonProperty("origin_name")
    private String originName;
    private String destination;
    @JsonProperty("destination_name")
    private String destinationName;
    @JsonProperty("departure_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd.MM.yy")
    private LocalDate departureDate;
    @JsonProperty("departure_time")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(pattern = "H:mm")
    private LocalTime departureTime;
    @JsonProperty("arrival_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd.MM.yy")
    private LocalDate arrivalDate;
    @JsonProperty("arrival_time")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonFormat(pattern = "H:mm")
    private LocalTime arrivalTime;
    private String carrier;
    private int stops;
    private double price;
}
