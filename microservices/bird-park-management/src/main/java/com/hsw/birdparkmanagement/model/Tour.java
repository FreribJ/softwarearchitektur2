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
@AllArgsConstructor
@Entity
public class Tour {

    @Id
    String name;
    String logo;
    double price;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL)
    Set<Attraction> attractions;

    @JoinColumn
    String test;

}
