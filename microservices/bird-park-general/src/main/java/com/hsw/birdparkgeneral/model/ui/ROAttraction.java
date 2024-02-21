package com.hsw.birdparkgeneral.model.ui;

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