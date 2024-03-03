package com.hsw.birdparkgeneral.api.ui;

import com.hsw.birdparkgeneral.model.ui.ROAttraction;
import com.hsw.birdparkgeneral.model.ui.ROMetadata;
import com.hsw.birdparkgeneral.service.PublicService;
import com.hsw.birdparkgeneral.model.ui.ROTour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080/", "http://192.168.0.196/"}, maxAge = 3600)
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
    public List<ROAttraction> attractions() {
        return this.publicService.getAllAttractions();
    }

    @GetMapping("/attraction/{name}")
    public ROAttraction attraction(@PathVariable String name) {
        return this.publicService.getAttraction(name);
    }

    @GetMapping("/attractionNames")
    public List<String> attractionNames() {
        return this.publicService.getAttractionNames();
    }

    //Tour
    @GetMapping("/tours")
    public List<ROTour> tours() {
        return this.publicService.getAllTours();
    }

    @GetMapping("/tour/{name}")
    public ROTour tour(@PathVariable String name) {
        return this.publicService.getTour(name);
    }

    @GetMapping("/tourNames")
    public List<String> tourNames() {
        return this.publicService.getTourNames();
    }
}
