package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.service.PrivateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Post", description = "Post Endpoints for the bird park management system")
public class ProtectedPost {

    PrivateService privateService;

    @Autowired
    public ProtectedPost(PrivateService privateService) {
        this.privateService = privateService;
    }

    @Operation(summary = "Create a new attraction")
    @ApiResponse(responseCode = "200", description = "Attraction created")
    @PostMapping("/attraction")
    public void createAttraction(@Parameter(description = "New attraction") @RequestBody ROAttraction roAttraction) {
        this.privateService.createAttraction(roAttraction);
    }

    @Operation(summary = "Create a new tour")
    @ApiResponse(responseCode = "200", description = "Tour created")
    @PostMapping("/tour")
    public void createTour(@Parameter(description = "New tour") @RequestBody ROTour roTour) {
        this.privateService.createTour(roTour);
    }
}
