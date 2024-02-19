package com.hsw.birdparkmanagement.model.ui;

import lombok.Data;

@Data
public class ROMetadata {
    static class Price {
        String category;
        int price;
    }

    static class OpeningHour {
        String hours;
        String info;
    }

    String name;
    String logo;
    String address;
    String description;
    Price[] prices;
    OpeningHour[] openingHours;
}
