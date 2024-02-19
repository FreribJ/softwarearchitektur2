package com.hsw.birdparkmanagement.model.ui;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import lombok.Data;

import java.util.List;

@Data
public class ROAttraction {

    public ROAttraction(Attraction attraction) {
        this.name = attraction.getName();
        this.logo = attraction.getLogo();
        this.tags = attraction.getTags();
        this.nearestTourNames = attraction.getNearestTours().stream().map(Tour::getName).toList();
        this.description = attraction.getDescription();
    }

    String name;
    String logo;
    String description;
    List<String> tags;
    List<String> nearestTourNames;
}
