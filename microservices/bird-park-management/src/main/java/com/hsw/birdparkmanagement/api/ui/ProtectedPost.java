package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.service.PrivateService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProtectedPost {

    PrivateService privateService;

    @Autowired
    public ProtectedPost(PrivateService privateService) {
        this.privateService = privateService;
    }

    @Parameter(description = "Create an attraction in the bird park", name = "attraction")
    @ApiResponse(responseCode = "200", description = "Attraction created")
    @PostMapping("/attraction")
    public void createAttraction(@Parameter(description = "New attraction") @RequestBody ROAttraction roAttraction) {
        this.privateService.createAttraction(roAttraction);
    }

    @Parameter(description = "Create a tour in the bird park", name = "tour")
    @ApiResponse(responseCode = "200", description = "Tour created")
    @PostMapping("/tour")
    public void createTour(@Parameter(description = "New tour") @RequestBody ROTour roTour) {
        this.privateService.createTour(roTour);
    }
}
