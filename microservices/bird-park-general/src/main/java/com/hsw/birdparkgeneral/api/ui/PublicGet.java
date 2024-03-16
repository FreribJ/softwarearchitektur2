package com.hsw.birdparkgeneral.api.ui;

import com.hsw.birdparkgeneral.model.ui.ROAttraction;
import com.hsw.birdparkgeneral.model.ui.ROMetadata;
import com.hsw.birdparkgeneral.service.PublicService;
import com.hsw.birdparkgeneral.model.ui.ROTour;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080/", "http://192.168.0.196/"}, maxAge = 3600)
@RestController
@Tag(name = "Public API", description = "Public Get Endpoints for the bird park management system")
public class PublicGet {

    PublicService publicService;

    @Autowired
    public PublicGet(PublicService publicService) {
        this.publicService = publicService;
    }

    //Metadata
    @Operation(summary = "Get metadata", description = "Get metadata of the bird park")
    @ApiResponse(responseCode = "200", description = "Metadata of the bird park")
    @GetMapping("/metadata")
    public ROMetadata metadata() {
        return this.publicService.getMetadata();
    }

    //Attraction
    @Operation(summary = "Get all attractions", description = "Get all attractions in the bird park")
    @ApiResponse(responseCode = "200", description = "All attractions in the bird park")
    @GetMapping("/attractions")
    public List<ROAttraction> attractions() {
        return this.publicService.getAllAttractions();
    }

    @Operation(summary = "Get an attraction", description = "Get an attraction in the bird park")
    @ApiResponse(responseCode = "200", description = "An attraction in the bird park")
    @GetMapping("/attraction/{name}")
    public ROAttraction attraction(@Parameter(description = "Name of the attraction") @PathVariable String name) {
        return this.publicService.getAttraction(name);
    }

    @Operation(summary = "Get all attraction names", description = "Get all attraction names in the bird park")
    @ApiResponse(responseCode = "200", description = "All attraction names in the bird park")
    @GetMapping("/attractionNames")
    public List<String> attractionNames() {
        return this.publicService.getAttractionNames();
    }

    //Tour
    @Operation(summary = "Get all tours", description = "Get all tours in the bird park")
    @ApiResponse(responseCode = "200", description = "All tours in the bird park")
    @GetMapping("/tours")
    public List<ROTour> tours() {
        return this.publicService.getAllTours();
    }

    @Operation(summary = "Get a tour", description = "Get a tour in the bird park")
    @ApiResponse(responseCode = "200", description = "A tour in the bird park")
    @GetMapping("/tour/{name}")
    public ROTour tour(@Parameter(description = "Name of the tour")@PathVariable String name) {
        return this.publicService.getTour(name);
    }

    @Operation(summary = "Get all tour names", description = "Get all tour names in the bird park")
    @ApiResponse(responseCode = "200", description = "All tour names in the bird park")
    @GetMapping("/tourNames")
    public List<String> tourNames() {
        return this.publicService.getTourNames();
    }
}
