package com.hsw.birdparkmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Metadata {
    @Id
    String name;
    String value;
    String type;
//    String logo;
//    String address;

//    public class Price {
//        String category;
//        double price;
//    }
//
//    List<Price> prices;
//    String[][] timetable = new String[7][3];
//    String description;
}
