package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.service.PrivateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Delete", description = "Delete Endpoints for the bird park management system")
public class ProtectedDelete {

    PrivateService privateService;

    @Autowired
    public ProtectedDelete(PrivateService privateService) {
        this.privateService = privateService;
    }

    @Operation(summary = "Delete a tour")
    @ApiResponse(responseCode = "200", description = "Tour deleted")
    @DeleteMapping("/tour/{name}")
    public void deleteTour(@Parameter(description = "Name of the tour") @PathVariable String name) {
        this.privateService.deleteTour(name);
    }

    @Operation(summary = "Delete an attraction")
    @ApiResponse(responseCode = "200", description = "Attraction deleted")
    @DeleteMapping("/attraction/{name}")
    public void deleteAttraction(@Parameter(description = "Name of the attraction") @PathVariable String name) {
        this.privateService.deleteAttraction(name);
    }
}
