package com.hsw.birdparkmanagement.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ROTour extends Tour{

    public ROTour(Tour tour) {
        this.name = tour.getName();
        this.logo = tour.getLogo();
        this.price = tour.getPrice();
        this.attractions = tour.getAttractions();
        this.test = tour.getTest();
    }

    List<String> attractionNames;
}
