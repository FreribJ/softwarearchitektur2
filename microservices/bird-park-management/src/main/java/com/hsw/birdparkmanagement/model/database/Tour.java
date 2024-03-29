package com.hsw.birdparkmanagement.model.database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tour {

    public Tour(ROTour roTour) {
        this.name = roTour.getName();
        this.logo = roTour.getLogo();
        this.price = roTour.getPrice();
    }

    @Id
    String name;
    String logo;
    String description;
    double price;
}
