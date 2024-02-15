package com.hsw.birdparkmanagement.service;

import com.hsw.birdparkmanagement.model.Attraction;
import com.hsw.birdparkmanagement.model.Metadata;
import com.hsw.birdparkmanagement.model.ROMetadata;
import com.hsw.birdparkmanagement.model.Tour;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PrivateService {

    AttractionRepository attractionRepository;
    TourRepository tourRepository;
    MetadataRepository metadataRepository;

    @Autowired
    public PrivateService(AttractionRepository attractionRepository, TourRepository tourRepository, MetadataRepository metadataRepository) {
        this.attractionRepository = attractionRepository;
        this.tourRepository = tourRepository;
        this.metadataRepository = metadataRepository;
    }

    public void createAttraction(Attraction attraction) {
        if (this.attractionRepository.existsById(attraction.getName()))
            throw new IllegalStateException("Attraction with name " + attraction.getName() + " already exists");
        this.attractionRepository.save(attraction);
    }

    public void createTour(Tour tour) {
        if (this.tourRepository.existsById(tour.getName()))
            throw new IllegalStateException("Tour with name " + tour.getName() + " already exists");
        this.tourRepository.save(tour);
    }

    public void deleteAttraction(String name) {
        if (!this.attractionRepository.existsById(name))
            throw new IllegalStateException("Attraction with name " + name + " does not exist");
        this.attractionRepository.deleteById(name);
    }

    public void deleteTour(String name) {
        if (!this.tourRepository.existsById(name))
            throw new IllegalStateException("Tour with name " + name + " does not exist");
        this.tourRepository.deleteById(name);
    }

    public void updateMetadata(ROMetadata roMetadata) {
        this.metadataRepository.findAll().forEach(data -> {
            switch (data.getName()) {
                case "name":
                    if (Objects.equals(data.getValue(), roMetadata.getName()))
                        break;
                    data.setValue(roMetadata.getName());
                    this.metadataRepository.save(data);
                    break;
                case "description":
                    if (Objects.equals(data.getValue(), roMetadata.getDescription()))
                        break;
                    data.setValue(roMetadata.getDescription());
                    this.metadataRepository.save(data);
                    break;
            }
        });
    }

    //TODO: abhängige Attraktionen auch aktualisieren
    public void updateAttraction(String name, Attraction attraction) {
        if (!this.attractionRepository.existsById(name))
            throw new IllegalStateException("Attraction with name " + name + " does not exist");
        if (!Objects.equals(name, attraction.getName()))
            this.attractionRepository.updateName(name, attraction.getName());
        this.attractionRepository.save(attraction);
    }

    //TODO: abhängige Touren auch aktualisieren
    public void updateTour(String name, Tour tour) {
        if (!this.tourRepository.existsById(name))
            throw new IllegalStateException("Tour with name " + name + " does not exist");
        if (!Objects.equals(name, tour.getName()))
            this.tourRepository.updateName(name, tour.getName());
        this.tourRepository.save(tour);

    }
}
