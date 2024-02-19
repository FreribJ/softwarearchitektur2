package com.hsw.birdparkmanagement.model.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class SubAttraction {

    public SubAttraction(Attraction attraction, String starttime, String endtime) {
        this.attraction = attraction;
        this.starttime = starttime;
        this.endtime = endtime;
        System.out.println("SubAttraction created");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Tour tour;

    @JsonIgnoreProperties("nearestTours")
    @OneToOne
    Attraction attraction;
    String starttime;
    String endtime;

}
