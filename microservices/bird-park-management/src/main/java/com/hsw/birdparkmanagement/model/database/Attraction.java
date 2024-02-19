package com.hsw.birdparkmanagement.model.database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Attraction {

    public Attraction(ROAttraction roAttraction) {
        this.name = roAttraction.getName();
        this.logo = roAttraction.getLogo();
        this.tags = roAttraction.getTags();
        this.description = roAttraction.getDescription();
    }

    @Id
    String name;

    String logo;

    @ElementCollection
    List<String> tags;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("subAttractions")
    Set<Tour> nearestTours;

    String description;

}
