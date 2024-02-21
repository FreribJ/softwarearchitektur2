package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.service.PrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost/", "http://192.168.0.196/"}, maxAge = 3600)
@RestController
public class ProtectedPut {

    PrivateService privateService;

    @Autowired
    public ProtectedPut(PrivateService privateService) {
        this.privateService = privateService;
    }

    @PutMapping("/tour/{name}")
    public void modifyTour(@PathVariable String name, @RequestBody ROTour roTour) {
        this.privateService.updateTour(name, roTour);
    }

    @PutMapping("/attraction/{name}")
    public void modifyAttraction(@PathVariable String name, @RequestBody ROAttraction roattraction) {
        this.privateService.updateAttraction(name, roattraction);
    }

    @PutMapping("/metadata")
    public void modifyMetadata(@RequestBody ROMetadata roMetadata) {
        this.privateService.updateMetadata(roMetadata);
    }
}
