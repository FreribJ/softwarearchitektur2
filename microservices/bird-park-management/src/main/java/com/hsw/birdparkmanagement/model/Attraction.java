package com.hsw.birdparkmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany
    Set<Tag> tags;

    @OneToMany
    List<Tour> nearestTour;
    String description;
    String animals;
}
