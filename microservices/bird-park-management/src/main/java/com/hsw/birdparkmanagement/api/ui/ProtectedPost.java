package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.service.PrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// TODO: alle Cors-Configurationen nochmal überprüfen
@CrossOrigin(origins = {"http://localhost:8080", "http://192.168.0.196"}, maxAge = 3600, methods = RequestMethod.POST)
@RestController
public class ProtectedPost {

    PrivateService privateService;

    @Autowired
    public ProtectedPost(PrivateService privateService) {
        this.privateService = privateService;
    }

    @PostMapping("/attraction")
    public void createAttraction(@RequestBody ROAttraction roAttraction) {
        this.privateService.createAttraction(roAttraction);
    }
    @PostMapping("/tour")
    public void createTour(@RequestBody ROTour roTour) {
        this.privateService.createTour(roTour);
    }
}
