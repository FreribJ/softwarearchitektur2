package com.hsw.birdparkgeneral.model.ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ROMetadata {
    public static class Price {
        @JsonProperty
        String category;
        @JsonProperty
        double price;
    }

    public static class OpeningHour {
        @JsonProperty
        String weekday;
        @JsonProperty
        String hours;
        @JsonProperty
        String info;
    }

    String name;
    String logo;
    String address;
    String description;
    Price[] prices;
    OpeningHour[] openingHours;
}
