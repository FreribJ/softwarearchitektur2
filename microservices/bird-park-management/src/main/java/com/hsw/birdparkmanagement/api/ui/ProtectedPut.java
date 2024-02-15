package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.Attraction;
import com.hsw.birdparkmanagement.model.Metadata;
import com.hsw.birdparkmanagement.model.ROMetadata;
import com.hsw.birdparkmanagement.model.Tour;
import com.hsw.birdparkmanagement.service.PrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedPut {

    PrivateService privateService;

    @Autowired
    public ProtectedPut(PrivateService privateService) {
        this.privateService = privateService;
    }

    @PutMapping("/tour/{name}")
    public void modifyTour(@PathVariable String name, @RequestBody Tour tour) {
        this.privateService.updateTour(name, tour);
    }

    @PutMapping("/attraction/{name}")
    public void modifyAttraction(@PathVariable String name, @RequestBody Attraction attraction) {
        System.out.println(name);
        System.out.println(attraction.toString());
        this.privateService.updateAttraction(name, attraction);
    }

    @PutMapping("/metadata")
    public void modifyMetadata(@RequestBody ROMetadata roMetadata) {
        this.privateService.updateMetadata(roMetadata);
    }
}
