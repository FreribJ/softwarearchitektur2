package com.hsw.birdparkmanagement.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.SubAttraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROSubAttraction;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    private <T> T convertToJsonNode(String value, Class<T> targetType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(mapper.readTree(value), targetType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ROMetadata getMetadata() {
        ROMetadata metadata = new ROMetadata();

        this.metadataRepository.findAll().forEach(data -> {
            switch (data.getName()) {
                case "name":
                    metadata.setName(data.getValue() + "TEST");
                    break;
                case "description":
                    metadata.setDescription(data.getValue());
                    break;
                case "logo":
                    metadata.setLogo(data.getValue());
                    break;
                case "address":
                    metadata.setAddress(data.getValue());
                    break;
                case "openingHours":
                    metadata.setOpeningHours(this.convertToJsonNode(data.getValue(), ROMetadata.OpeningHour[].class));
                    break;
                case "prices":
                    metadata.setPrices(this.convertToJsonNode(data.getValue(), ROMetadata.Price[].class));
                    break;
            }
        });
        return metadata;
    }


    //Attractions
    private ROAttraction convertAttraction(Attraction attraction) {
        if (attraction == null) {
            return null;
        }
        List<String> nearestTourNames = this.tourRepository.findAllByAttractionName(attraction.getName());
        return ROAttraction.builder()
                .name(attraction.getName())
                .logo(attraction.getLogo())
                .tags(attraction.getTags())
                .description(attraction.getDescription())
                .nearestTourNames(nearestTourNames).build();
    }

    //TODO: Mit ResponeEntity ausprobieren
    public List<ROAttraction> getAllAttractions() {
        return this.attractionRepository.findAll().stream().map(this::convertAttraction
        ).collect(Collectors.toList());
    }

    public ROAttraction getAttraction(String name) {
        return this.convertAttraction(this.attractionRepository.findById(name).orElse(null));
    }

    public List<String> getAttractionNames() {
        return this.attractionRepository.getAttractionNames();
    }


    //Tours
    private ROSubAttraction convertSubAttraction(SubAttraction subAttraction) {
        if (subAttraction == null) {
            return null;
        }
        return ROSubAttraction.builder()
                .end(subAttraction.getEndtime())
                .begin(subAttraction.getStarttime())
                .attraction(subAttraction.getAttraction().getName()).build();
    }

    private ROTour convertTour(Tour tour) {
        if (tour == null) {
            return null;
        }
        return ROTour.builder()
                .name(tour.getName())
                .logo(tour.getLogo())
                .price(tour.getPrice())
                .description(tour.getDescription())
                .attractions(tour.getSubAttractions().stream().map(this::convertSubAttraction).collect(Collectors.toList())).build();
    }

    public List<ROTour> getAllTours() {
        return this.tourRepository.findAll().stream().map(this::convertTour
        ).collect(Collectors.toList());
    }

    public ROTour getTour(String name) {
        return this.convertTour(this.tourRepository.findById(name).orElse(null));
    }

    public List<String> getTourNames() {
        return this.tourRepository.getTourNames();
    }
}
