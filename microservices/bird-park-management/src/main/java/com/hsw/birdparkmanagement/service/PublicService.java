package com.hsw.birdparkmanagement.service;

import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                    break;
                case "logo":
                    metadata.setLogo(data.getValue());
                    break;
                case "address":
                    metadata.setAddress(data.getValue());
                    break;

            }
        });
        return metadata;
    }


    //Attractions
    public Iterable<Attraction> getAllAttractions() {
//        List<ROAttraction> attractions = new ArrayList<ROAttraction>();
//        this.attractionRepository.findAll().forEach(attraction -> {
//            ROAttraction roAttraction = new ROAttraction(attraction);
//            roAttraction.setNearestTourNames(this.attractionRepository.getNearestTourNames(attraction.getName()));
//            attractions.add(roAttraction);
//        });
//        return attractions;
        return this.attractionRepository.findAll();
    }

    public Optional<Attraction> getAttraction(String name) {
//        Attraction attraction = this.attractionRepository.findById(name).orElse(null);
//        if (attraction == null) {
//            return Optional.empty();
//        }
//        ROAttraction roAttraction = new ROAttraction(attraction);
//        roAttraction.setNearestTourNames(this.attractionRepository.getNearestTourNames(name));
//        return Optional.of(roAttraction);
        return this.attractionRepository.findById(name);
    }

    public List<String> getAttractionNames() {
        return this.attractionRepository.getAttractionNames();
    }


    //Tours
    public List<Tour> getAllTours() {
//        List<ROTour> tours = new ArrayList<ROTour>();
//        this.tourRepository.findAll().forEach(tour -> {
//            ROTour roTour = new ROTour(tour);
////            roTour.setAttractionNames(this.tourRepository.getAttractions(tour.getName()));
//            tours.add(roTour);
//        });
//        return tours;
        return this.tourRepository.findAll();
    }

    public Optional<Tour> getTour(String name) {
//        Tour tour = this.tourRepository.findById(name).orElse(null);
//        if (tour == null) {
//            return Optional.empty();
//        }
//        ROTour roTour = new ROTour(tour);
////        roTour.setAttractionNames(this.tourRepository.getAttractions(name));
//        return Optional.of(roTour);
        return this.tourRepository.findById(name);
    }

    public List<String> getTourNames() {
        return this.tourRepository.getTourNames();
    }

    public String test() {
        return "test";
    }
}
