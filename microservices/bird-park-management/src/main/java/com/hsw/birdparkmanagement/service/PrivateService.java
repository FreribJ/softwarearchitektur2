package com.hsw.birdparkmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.SubAttraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PrivateService {

    AttractionRepository attractionRepository;
    TourRepository tourRepository;
    MetadataRepository metadataRepository;
    ObjectMapper mapper;

    @Autowired
    public PrivateService(AttractionRepository attractionRepository, TourRepository tourRepository, MetadataRepository metadataRepository) {
        this.attractionRepository = attractionRepository;
        this.tourRepository = tourRepository;
        this.metadataRepository = metadataRepository;
        this.mapper = new ObjectMapper();

        //Initial Database setup
        if (this.metadataRepository.count() == 0) {
            this.updateMetadata(ROMetadata.builder()
                    .name("Bird Park HSW")
                    .address("Am Stockhof 2, 31785 Hameln")
                    .description("Welcome to the Bird Park")
                    .logo("https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Hochschule_Weserbergland_logo.svg/2560px-Hochschule_Weserbergland_logo.svg.png")
                    .prices(new ROMetadata.Price[]{new ROMetadata.Price("Adult", 20.0), new ROMetadata.Price("Child", 10.0)})
                    .openingHours(new ROMetadata.OpeningHour[]{new ROMetadata.OpeningHour("Wochentag", "9:00 - 18:00", "Außer an Feiertagen"), new ROMetadata.OpeningHour("Wochenende", "9:00 - 18:00", "")})
                    .build());
        }
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

        System.out.println(rotour.toString());

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

    //TODO: Abhängigkeiten lassen sich nicht löschen!
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

    private String convertJSONToString(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalStateException("Could not convert object to JSON");
        }
    }

    public void updateMetadata(ROMetadata roMetadata) {
        this.metadataRepository.findAll().forEach(data -> {
            switch (data.getName()) {
                case "name":
                    if (Objects.equals(data.getValue(), roMetadata.getName()) || roMetadata.getName() == null || roMetadata.getName().isEmpty())
                        break;
                    data.setValue(roMetadata.getName());
                    this.metadataRepository.save(data);
                    break;
                case "description":
                    if (Objects.equals(data.getValue(), roMetadata.getDescription()) || roMetadata.getDescription() == null)
                        break;
                    data.setValue(roMetadata.getDescription());
                    this.metadataRepository.save(data);
                    break;
                case "logo":
                    if (Objects.equals(data.getValue(), roMetadata.getLogo()) || roMetadata.getLogo() == null)
                        break;
                    data.setValue(roMetadata.getLogo());
                    this.metadataRepository.save(data);
                    break;
                case "address":
                    if (Objects.equals(data.getValue(), roMetadata.getAddress()) || roMetadata.getAddress() == null)
                        break;
                    data.setValue(roMetadata.getAddress());
                    this.metadataRepository.save(data);
                    break;
                case "prices":
                    String pricesString = this.convertJSONToString(roMetadata.getPrices());
                    if (Objects.equals(data.getValue(), pricesString) || roMetadata.getPrices() == null)
                        break;
                    data.setValue(pricesString);
                    this.metadataRepository.save(data);
                    break;
                case "openingHours":
                    String openingHoursString = this.convertJSONToString(roMetadata.getOpeningHours());
                    if (Objects.equals(data.getValue(), openingHoursString) || roMetadata.getOpeningHours() == null)
                        break;
                    data.setValue(openingHoursString);
                    this.metadataRepository.save(data);
                    break;
            }
        });
    }

    //TODO: abhängige Attraktionen auch aktualisieren
    @Transactional
    public void updateAttraction(String name, ROAttraction roattraction) {
        if (!this.attractionRepository.existsById(name))
            throw new IllegalStateException("Attraction with name " + name + " does not exist");

        Attraction attraction = Attraction.builder()
                .name(roattraction.getName())
                .description(roattraction.getDescription())
                .logo(roattraction.getLogo())
                .tags(roattraction.getTags())
                .build();
        if (!Objects.equals(name, roattraction.getName()))
            this.attractionRepository.updateName(name, roattraction.getName());
        this.attractionRepository.save(attraction);
    }

    //    TODO: abhängige Touren auch aktualisieren
    public void updateTour(String name, ROTour rotour) {
        if (!this.tourRepository.existsById(name))
            throw new IllegalStateException("Tour with name " + name + " does not exist");

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

        if (!Objects.equals(name, rotour.getName()))
            this.tourRepository.updateName(name, rotour.getName());
        this.tourRepository.save(tour);
    }
}
