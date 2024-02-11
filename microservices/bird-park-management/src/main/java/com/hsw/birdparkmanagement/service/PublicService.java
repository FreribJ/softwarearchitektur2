package com.hsw.birdparkmanagement.service;

import com.hsw.birdparkmanagement.model.Attraction;
import com.hsw.birdparkmanagement.model.Metadata;
import com.hsw.birdparkmanagement.model.ROMetadata;
import com.hsw.birdparkmanagement.model.Tour;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicService {

    private MetadataRepository metadataRepository;
    private AttractionRepository attractionRepository;
    private TourRepository tourRepository;

    @Autowired
    public PublicService(MetadataRepository metadataRepository, AttractionRepository attractionRepository, TourRepository tourRepository) {
        this.metadataRepository = metadataRepository;
        this.attractionRepository = attractionRepository;
        this.tourRepository = tourRepository;
    }

    public ROMetadata getMetadata() {
        ROMetadata metadata = new ROMetadata();

        this.metadataRepository.findAll().forEach(data -> {
            switch (data.getName()) {
                case "name":
                    metadata.setName(data.getValue());
                    break;
                case "description":
                    metadata.setDescription(data.getValue());
            }
        });
        return metadata;
    }

    public Iterable<Attraction> getAttractions() {
        return this.attractionRepository.findAll();
    }

    //Attractions
    public Iterable<Attraction> getAllAttractions() {
        return this.attractionRepository.findAll();
    }

    public Optional<Attraction> getAttraction(String name) {
        return this.attractionRepository.findById(name);
    }

    public List<String> getAttractionNames() {
        return null;
        //return this.attractionRepository.getAttractionNames();
    }


    //Tours
    public Iterable<Tour> getAllTours() {
        return this.tourRepository.findAll();
    }

    public Optional<Tour> getTour(String name) {
        return this.tourRepository.findById(name);
    }

    public Iterable<Tour> getTours() {
        return this.tourRepository.findAll();
    }

    public List<String> getTourNames() {
        return null;
    }
}
