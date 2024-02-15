package com.hsw.birdparkmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
public class Attraction {

    @Id
    String name;
    String logo;

    @ElementCollection
    List<String> tags;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL)
    Set<Tour> nearestTour;

    String description;
    String animals;

}
