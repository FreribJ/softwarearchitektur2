package com.hsw.birdparkgeneral.model.ui;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ROTour {

    String name;
    String logo;
    double price;
    String description;
    List<ROSubAttraction> attractions;
}
