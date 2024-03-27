package com.hsw.birdparkmanagement.model.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ROInAttraction {
    String name;
    String logo;
    String description;
    List<String> tags;
}