package com.hsw.birdparkmanagement.model.ui;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ROInAttraction {
    String name;
    String logo;
    String description;
    List<String> tags;
}