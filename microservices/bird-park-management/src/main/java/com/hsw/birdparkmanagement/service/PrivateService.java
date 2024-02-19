package com.hsw.birdparkmanagement.service;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.SubAttraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

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

    public void createAttraction(ROAttraction roattraction) {
        if (this.attractionRepository.existsById(roattraction.getName()))
            throw new IllegalStateException("Attraction with name " + roattraction.getName() + " already exists");

        Attraction attraction = Attraction.builder()
                .name(roattraction.getName())
                .description(roattraction.getDescription())
                .logo(roattraction.getLogo())
                .tags(roattraction.getTags())
                .build();
        this.attractionRepository.save(attraction);
    }

    public void createTour(ROTour rotour) {
        if (this.tourRepository.existsById(rotour.getName()))
            throw new IllegalStateException("Tour with name " + rotour.getName() + " already exists");

        Tour tour = Tour.builder()
                .name(rotour.getName())
                .description(rotour.getDescription())
                .logo(rotour.getLogo())
                .subAttractions(rotour.getAttractions().stream()
                        .map(roSubAttraction -> SubAttraction.builder()
                                .starttime(roSubAttraction.getBegin())
                                .endtime(roSubAttraction.getEnd())
                                .attraction(this.attractionRepository.findById(roSubAttraction.getAttraction()).orElseThrow(() ->
                                        new IllegalStateException("Attraction with name " + roSubAttraction.getAttraction() + " does not exist")
                                )).build())
                        .collect(Collectors.toList()))
                .build();
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
                case "logo":
                    if (Objects.equals(data.getValue(), roMetadata.getLogo()))
                        break;
                    data.setValue(roMetadata.getLogo());
                    this.metadataRepository.save(data);
                    break;
                case "address":
                    if (Objects.equals(data.getValue(), roMetadata.getAddress()))
                        break;
                    data.setValue(roMetadata.getAddress());
                    this.metadataRepository.save(data);
                    break;

            }
        });
    }

    //TODO: abhängige Attraktionen auch aktualisieren
    public void updateAttraction(String name, ROAttraction roattraction) {
        if (!this.attractionRepository.existsById(name))
            throw new IllegalStateException("Attraction with name " + name + " does not exist");

        Attraction attraction = new Attraction(roattraction);
        roattraction.getNearestTourNames().forEach(tourName ->
                this.tourRepository.findById(tourName).ifPresent(tour -> {
                    attraction.getNearestTours().add(tour);
                }));

        if (!Objects.equals(name, attraction.getName()))
            this.attractionRepository.updateName(name, attraction.getName());
        this.attractionRepository.save(attraction);
    }

//    TODO: abhängige Touren auch aktualisieren
    public void updateTour(String name, ROTour rotour) {
        if (!this.tourRepository.existsById(name))
            throw new IllegalStateException("Tour with name " + name + " does not exist");

        Tour tour = new Tour(rotour);
        rotour.getSubAttractions().forEach(subAttraction ->
                this.attractionRepository.findById(subAttraction.getAttractionName()).ifPresent(attraction -> {
                    tour.getSubAttractions().add(new SubAttraction(attraction, subAttraction.getStarttime(), subAttraction.getEndtime()));
                }));

        if (!Objects.equals(name, tour.getName()))
            this.tourRepository.updateName(name, tour.getName());
        this.tourRepository.save(tour);

    }
}
