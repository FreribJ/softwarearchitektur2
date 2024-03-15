package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.service.PrivateService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @Parameter(description = "Delete a tour in the bird park", name = "tour")
    @ApiResponse(responseCode = "200", description = "Tour deleted")
    @DeleteMapping("/tour/{name}")
    public void deleteTour(@Parameter(description = "Name of the tour") @PathVariable String name) {
        this.privateService.deleteTour(name);
    }

    @Parameter(description = "Delete an attraction in the bird park", name = "attraction")
    @ApiResponse(responseCode = "200", description = "Attraction deleted")
    @DeleteMapping("/attraction/{name}")
    public void deleteAttraction(@Parameter(description = "Name of the attraction") @PathVariable String name) {
        this.privateService.deleteAttraction(name);
    }
}
