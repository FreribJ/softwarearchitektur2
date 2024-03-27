package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.ui.ROInAttraction;
import com.hsw.birdparkmanagement.model.ui.ROOutAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.service.PrivateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "Put", description = "Put Endpoints for the bird park management system")
public class ProtectedPut {

    PrivateService privateService;

    @Autowired
    public ProtectedPut(PrivateService privateService) {
        this.privateService = privateService;
    }

    @Operation(summary = "Modify tour")
    @ApiResponse(responseCode = "200", description = "Tour modified")
    @PutMapping("/tour/{name}")
    public void modifyTour(@Parameter(description = "Name of the attraction") @PathVariable String name, @Parameter(description = "Edited tour") @RequestBody ROTour roTour) {
        this.privateService.updateTour(name, roTour);
    }

    @Operation(summary = "Modify attraction")
    @ApiResponse(responseCode = "200", description = "Attraction modified")
    @PutMapping("/attraction/{name}")
    public void modifyAttraction(@Parameter(description = "Name of the attraction") @PathVariable String name,  @Parameter(description = "Edited attraction") @RequestBody ROInAttraction roInAttraction) {
        this.privateService.updateAttraction(name, roInAttraction);
    }

    @Operation(summary = "Modify metadata")
    @ApiResponse(responseCode = "200", description = "Metadata modified")
    @PutMapping("/metadata")
    public void modifyMetadata(@Parameter(description = "Changed metadata") @RequestBody ROMetadata roMetadata) {
        this.privateService.updateMetadata(roMetadata);
    }
}
