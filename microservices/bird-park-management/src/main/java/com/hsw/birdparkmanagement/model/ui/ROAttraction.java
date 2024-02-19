package com.hsw.birdparkmanagement.model.ui;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ROAttraction {
    String name;
    String logo;
    String description;
    List<String> tags;
    List<String> nearestTourNames;
}