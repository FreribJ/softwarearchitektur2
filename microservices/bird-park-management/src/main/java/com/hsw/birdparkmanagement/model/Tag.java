package com.hsw.birdparkmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Tag {
    @Id
    String name;
}
