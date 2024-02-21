package com.hsw.birdparkgeneral.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsw.birdparkgeneral.api.spi.ManagmentSPI;
import com.hsw.birdparkgeneral.model.ui.ROAttraction;
import com.hsw.birdparkgeneral.model.ui.ROMetadata;
import com.hsw.birdparkgeneral.model.ui.ROTour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicService {

    ManagmentSPI managmentSPI;

    @Autowired
    public PublicService(ManagmentSPI managmentSPI) {
        this.managmentSPI = managmentSPI;
    }


    //Metadata
    public ROMetadata getMetadata() {
        return this.managmentSPI.getMetadata();
    }

    //Attraction
    public List<ROAttraction> getAllAttractions() {
        return this.managmentSPI.getRoAttractions();
    }

    public ROAttraction getAttraction(String name) {
        return this.managmentSPI.getAttraction(name);
    }

    public List<String> getAttractionNames() {
        return this.managmentSPI.attractionNames();
    }

    //Tour
    public List<ROTour> getAllTours() {
        return this.managmentSPI.tours();
    }

    public ROTour getTour(String name) {
        return this.managmentSPI.tour(name);
    }

    public List<String> getTourNames() {
        return this.managmentSPI.tourNames();
    }


}
