package com.hsw.birdparkmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Tag {
    @Id
    String name;
}
