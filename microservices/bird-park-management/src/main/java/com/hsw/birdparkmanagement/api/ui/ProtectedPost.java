package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.service.PrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedPost {
//
//    PrivateService privateService;
//
//    @Autowired
//    public ProtectedPost(PrivateService privateService) {
//        this.privateService = privateService;
//    }
//
//    @PostMapping("/attraction")
//    public void createAttraction(@RequestBody Attraction attraction) {
//        this.privateService.createAttraction(attraction);
//    }
//    @PostMapping("/tour")
//    public void createTour(@RequestBody Tour tour) {
//        this.privateService.createTour(tour);
//    }
}
