package com.hsw.birdparkmanagement.model.ui;

import com.hsw.birdparkmanagement.model.database.Tour;
import lombok.Data;

import java.util.List;

@Data
public class ROTour {

    public ROTour(Tour tour) {
        this.name = tour.getName();
        this.logo = tour.getLogo();
        this.price = tour.getPrice();
  }

    String name;
    String logo;
    double price;

    List<ROSubAttraction> subAttractions;
}
