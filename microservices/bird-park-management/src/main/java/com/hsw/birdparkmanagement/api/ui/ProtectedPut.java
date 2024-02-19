package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.service.PrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedPut {

//    PrivateService privateService;
//
//    @Autowired
//    public ProtectedPut(PrivateService privateService) {
//        this.privateService = privateService;
//    }
//
////    @PutMapping("/tour/{name}")
////    public void modifyTour(@PathVariable String name, @RequestBody ROTour rotour) {
////        this.privateService.updateTour(name, rotour);
////    }
//
//    @PutMapping("/attraction/{name}")
//    public void modifyAttraction(@PathVariable String name, @RequestBody ROAttraction roattraction) {
//        this.privateService.updateAttraction(name, roattraction);
//    }
//
//    @PutMapping("/metadata")
//    public void modifyMetadata(@RequestBody ROMetadata roMetadata) {
//        this.privateService.updateMetadata(roMetadata);
//    }
}
