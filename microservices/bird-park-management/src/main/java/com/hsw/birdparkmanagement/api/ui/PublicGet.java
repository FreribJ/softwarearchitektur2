package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.Attraction;
import com.hsw.birdparkmanagement.model.Metadata;
import com.hsw.birdparkmanagement.model.ROMetadata;
import com.hsw.birdparkmanagement.model.Tour;
import com.hsw.birdparkmanagement.service.PublicService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PublicGet {

    PublicService publicService;

    @Autowired
    public PublicGet(PublicService publicService) {
        this.publicService = publicService;
    }

    //Metadata
    @GetMapping("/metadata")
    public ROMetadata metadata() {
        return this.publicService.getMetadata();
    }

    //Attraction
    @GetMapping("/attractions")
    public Iterable<Attraction> attractions() {
        return this.publicService.getAttractions();
    }

    @GetMapping("/attraction")
    public Optional<Attraction> attraction() {
        return this.publicService.getAttraction("test");
    }

    @GetMapping("/attractionNames")
    public List<String> attractionNames() {
        return this.publicService.getAttractionNames();
    }

    //Tour
    @GetMapping("/tours")
    public Iterable<Tour> tours() {
        return this.publicService.getTours();
    }

    @GetMapping("/tour/:name")
    public Optional<Tour> tour(@PathParam("name") String name) {
        return this.publicService.getTour(name);
    }

    @GetMapping("/tourNames")
    public List<String> tourNames() {
        return this.publicService.getTourNames();
    }

}
