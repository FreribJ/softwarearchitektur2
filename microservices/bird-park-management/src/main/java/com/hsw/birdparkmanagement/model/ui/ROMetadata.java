package com.hsw.birdparkmanagement.model.ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ROMetadata {
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Price {
        @JsonProperty
        String category;
        @JsonProperty
        double price;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
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
