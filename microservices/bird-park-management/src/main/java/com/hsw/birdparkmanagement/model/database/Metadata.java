package com.hsw.birdparkmanagement.model.database;

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
}
