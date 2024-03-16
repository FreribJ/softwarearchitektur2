package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.service.PrivateService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProtectedPut {

    PrivateService privateService;

    @Autowired
    public ProtectedPut(PrivateService privateService) {
        this.privateService = privateService;
    }

    @Parameter(description = "Modify a tour in the bird park", name = "tour")
    @ApiResponse(responseCode = "200", description = "Tour modified")
    @PutMapping("/tour/{name}")
    public void modifyTour(@Parameter(description = "Name of the attraction") @PathVariable String name, @Parameter(description = "Edited tour") @RequestBody ROTour roTour) {
        this.privateService.updateTour(name, roTour);
    }

    @Parameter(description = "Modify an attraction in the bird park", name = "attraction")
    @ApiResponse(responseCode = "200", description = "Attraction modified")
    @PutMapping("/attraction/{name}")
    public void modifyAttraction(@Parameter(description = "Name of the attraction") @PathVariable String name,  @Parameter(description = "Edited attraction") @RequestBody ROAttraction roattraction) {
        this.privateService.updateAttraction(name, roattraction);
    }

    @Parameter(description = "Modify the metadata of the bird park", name = "metadata")
    @ApiResponse(responseCode = "200", description = "Metadata modified")
    @PutMapping("/metadata")
    public void modifyMetadata(@Parameter(description = "Changed metadata") @RequestBody ROMetadata roMetadata) {
        this.privateService.updateMetadata(roMetadata);
    }
}
