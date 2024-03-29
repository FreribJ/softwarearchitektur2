package com.hsw.birdparkmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsw.birdparkmanagement.Exceptions.BadArgumentException;
import com.hsw.birdparkmanagement.Exceptions.NotFoundException;
import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.AttractionTag;
import com.hsw.birdparkmanagement.model.database.SubAttraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROOutAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROSubAttraction;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicService {

    private MetadataRepository metadataRepository;
    private AttractionRepository attractionRepository;
    private TourRepository tourRepository;
    private SubAttractionRepository subAttractionRepository;
    private AttractionTagRepository attractionTagRepository;

    @Autowired
    public PublicService(MetadataRepository metadataRepository, AttractionRepository attractionRepository, TourRepository tourRepository, SubAttractionRepository subAttractionRepository, AttractionTagRepository attractionTagRepository) {
        this.metadataRepository = metadataRepository;
        this.attractionRepository = attractionRepository;
        this.tourRepository = tourRepository;
        this.subAttractionRepository = subAttractionRepository;
        this.attractionTagRepository = attractionTagRepository;
    }


    private <T> T convertToJsonNode(String value, Class<T> targetType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(mapper.readTree(value), targetType);
        } catch (Exception e) {
            return null;
        }
    }

    public ROMetadata getMetadata() {
        ROMetadata metadata = new ROMetadata();

        this.metadataRepository.findAll().forEach(data -> {
            switch (data.getM_name()) {
                case "name":
                    metadata.setName(data.getM_value());
                    break;
                case "description":
                    metadata.setDescription(data.getM_value());
                    break;
                case "logo":
                    metadata.setLogo(data.getM_value());
                    break;
                case "address":
                    metadata.setAddress(data.getM_value());
                    break;
                case "openingHours":
                    metadata.setOpeningHours(this.convertToJsonNode(data.getM_value(), ROMetadata.OpeningHour[].class));
                    break;
                case "prices":
                    metadata.setPrices(this.convertToJsonNode(data.getM_value(), ROMetadata.Price[].class));
                    break;
            }
        });
        return metadata;
    }


    //Attractions
    private ROOutAttraction convertAttraction(Attraction attraction) {
        if (attraction == null) {
            return null;
        }
        List<String> nearestTourNames = this.subAttractionRepository.findAllByAttractionToTour(attraction.getName()).stream().map(SubAttraction::getTour).collect(Collectors.toList());
        return ROOutAttraction.builder()
                .name(attraction.getName())
                .logo(attraction.getLogo())
                .tags(this.attractionTagRepository.findAllByAttractionName(attraction.getName()).stream().map(AttractionTag::getTag).collect(Collectors.toList()))
                .description(attraction.getDescription())
                .nearestTourNames(nearestTourNames).build();
    }

    //TODO: Mit ResponeEntity ausprobieren
    public List<ROOutAttraction> getAllAttractions() {
        return this.attractionRepository.findAll().stream().map(this::convertAttraction
        ).collect(Collectors.toList());
    }

    public ROOutAttraction getAttraction(String name) {
        if (name == null || name.isBlank()) {
            throw new BadArgumentException("Name cannot be empty");
        }
        return this.convertAttraction(this.attractionRepository.findById(name).orElseThrow(() -> new NotFoundException("Attraction")));
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
                .attraction(subAttraction.getAttractionToTour()).build();
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
                .attractions(this.subAttractionRepository.findAllByTour(tour.getName()).stream().map(this::convertSubAttraction).collect(Collectors.toList())).build();
    }

    public List<ROTour> getAllTours() {
        return this.tourRepository.findAll().stream().map(this::convertTour
        ).collect(Collectors.toList());
    }

    public ROTour getTour(String name) {
        if (name == null || name.isBlank()) {
            throw new BadArgumentException("Name cannot be empty");
        }
        return this.convertTour(this.tourRepository.findById(name).orElseThrow(() -> new NotFoundException("Tour")));
    }

    public List<String> getTourNames() {
        return this.tourRepository.getTourNames();
    }
}
