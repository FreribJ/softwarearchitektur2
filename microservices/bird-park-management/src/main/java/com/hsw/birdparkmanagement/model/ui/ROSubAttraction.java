package com.hsw.birdparkmanagement.model.ui;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ROSubAttraction {

    String attraction;
    String begin;
    String end;
}
