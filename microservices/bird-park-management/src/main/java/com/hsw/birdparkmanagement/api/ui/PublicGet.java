package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.service.PublicService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Public", description = "Public API")
public class PublicGet {

    PublicService publicService;

    @Autowired
    public PublicGet(PublicService publicService) {
        this.publicService = publicService;
    }

    //Metadata
    @Parameter(description = "Get the metadata of the bird park", name = "metadata")
    @ApiResponse(responseCode = "200", description = "Metadata of the bird park")
    @GetMapping("/metadata")
    public ROMetadata metadata() {
        return this.publicService.getMetadata();
    }

    //Attraction
    @Parameter(description = "Get all attractions in the bird park", name = "attractions")
    @ApiResponse(responseCode = "200", description = "All attractions in the bird park")
    @GetMapping("/attractions")
    public List<ROAttraction> attractions() {
        return this.publicService.getAllAttractions();
    }

    @Parameter(description = "Get a specific attraction in the bird park", name = "attraction")
    @ApiResponse(responseCode = "200", description = "Specific attraction in the bird park")
    @GetMapping("/attraction/{name}")
    public ROAttraction attraction(@Parameter(description = "Name of the attraction") @PathVariable String name) {
        return this.publicService.getAttraction(name);
    }

    @Parameter(description = "Get all attraction names in the bird park", name = "attractionNames")
    @ApiResponse(responseCode = "200", description = "All attraction names in the bird park")
    @GetMapping("/attractionNames")
    public List<String> attractionNames() {
        return this.publicService.getAttractionNames();
    }

    //Tour
    @Parameter(description = "Get all tours in the bird park", name = "tours")
    @ApiResponse(responseCode = "200", description = "All tours in the bird park")
    @GetMapping("/tours")
    public List<ROTour> tours() {
        return this.publicService.getAllTours();
    }

    @Parameter(description = "Get a specific tour in the bird park", name = "tour")
    @ApiResponse(responseCode = "200", description = "Specific tour in the bird park")
    @GetMapping("/tour/{name}")
    public ROTour tour(@Parameter(description = "Name of the tour") @PathVariable String name) {
        return this.publicService.getTour(name);
    }

    @Parameter(description = "Get all tour names in the bird park", name = "tourNames")
    @ApiResponse(responseCode = "200", description = "All tour names in the bird park")
    @GetMapping("/tourNames")
    public List<String> tourNames() {
        return this.publicService.getTourNames();
    }
}
