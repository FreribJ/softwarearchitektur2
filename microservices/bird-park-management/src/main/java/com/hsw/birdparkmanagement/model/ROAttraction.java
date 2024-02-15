package com.hsw.birdparkmanagement.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Data
public class ROAttraction extends Attraction {

    public ROAttraction(Attraction attraction) {
        this.name = attraction.getName();
        this.logo = attraction.getLogo();
        this.tags = attraction.getTags();
        this.nearestTour = attraction.getNearestTour();
        this.description = attraction.getDescription();
        this.animals = attraction.getAnimals();
    }

    List<String> nearestTourNames;
}
