package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.service.PrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedDelete {

    PrivateService privateService;

    @Autowired
    public ProtectedDelete(PrivateService privateService) {
        this.privateService = privateService;
    }

    @DeleteMapping("/tour/{name}")
    public void deleteTour(@PathVariable String name) {
        this.privateService.deleteTour(name);
    }

    @DeleteMapping("/attraction/{name}")
    public void deleteAttraction(@PathVariable String name) {
        this.privateService.deleteAttraction(name);
    }
}
