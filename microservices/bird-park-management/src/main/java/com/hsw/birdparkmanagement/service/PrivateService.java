package com.hsw.birdparkmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsw.birdparkmanagement.Exceptions.BadArgumentException;
import com.hsw.birdparkmanagement.Exceptions.UnexpectedErrorException;
import com.hsw.birdparkmanagement.model.database.*;
import com.hsw.birdparkmanagement.model.ui.ROInAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROSubAttraction;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PrivateService {

    AttractionRepository attractionRepository;
    TourRepository tourRepository;
    MetadataRepository metadataRepository;
    SubAttractionRepository subAttractionRepository;
    AttractionTagRepository attractionTagRepository;
    ObjectMapper mapper;

    @Autowired
    public PrivateService(AttractionRepository attractionRepository, TourRepository tourRepository, MetadataRepository metadataRepository, SubAttractionRepository subAttractionRepository, AttractionTagRepository attractionTagRepository) {
        this.attractionRepository = attractionRepository;
        this.tourRepository = tourRepository;
        this.metadataRepository = metadataRepository;
        this.subAttractionRepository = subAttractionRepository;
        this.attractionTagRepository = attractionTagRepository;
        this.mapper = new ObjectMapper();

        //Initial Database setup
        if (this.metadataRepository.count() == 0) {
            List<Metadata> metadata = new ArrayList<>();
            metadata.add(Metadata.builder().m_name("name").m_type("String").m_value("Bird Park HSW").build());
            metadata.add(Metadata.builder().m_name("description").m_type("String").m_value("Welcome to the Bird Park").build());
            metadata.add(Metadata.builder().m_name("logo").m_type("String").m_value("https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Hochschule_Weserbergland_logo.svg/2560px-Hochschule_Weserbergland_logo.svg.png").build());
            metadata.add(Metadata.builder().m_name("address").m_type("String").m_value("Am Stockhof 2, 31785 Hameln").build());
            metadata.add(Metadata.builder().m_name("prices").m_type("JSON").m_value("[{\"category\":\"Adult\",\"price\":20.0},{\"category\":\"Child\",\"price\":10.0}]").build());
            metadata.add(Metadata.builder().m_name("openingHours").m_type("JSON").m_value("[{\"weekday\":\"Wochentag\",\"hours\":\"9:00 - 18:00\",\"info\":\"Außer an Feiertagen\"},{\"weekday\":\"Wochenende\",\"hours\":\"9:00 - 18:00\",\"info\":\"\"}]").build());

            this.metadataRepository.saveAll(metadata);

            List<Attraction> attractions = new ArrayList<>();
            attractions.add(Attraction.builder().name("attractionName1").description("attractionDescription").logo("https://www.attractionLogo.png").build());
            attractions.add(Attraction.builder().name("attractionName2").description("attractionDescription").logo("https://www.attractionLogo.png").build());
            attractions.add(Attraction.builder().name("attractionName3").description("attractionDescription").logo("https://www.attractionLogo.png").build());

            List<AttractionTag> attractionTags = new ArrayList<>();
            attractionTags.add(AttractionTag.builder().attractionName("attractionName1").tag("tag1").build());
            attractionTags.add(AttractionTag.builder().attractionName("attractionName1").tag("tag2").build());
            attractionTags.add(AttractionTag.builder().attractionName("attractionName2").tag("tag1").build());
            attractionTags.add(AttractionTag.builder().attractionName("attractionName3").tag("tag2").build());
            attractionTags.add(AttractionTag.builder().attractionName("attractionName3").tag("tag3").build());

            this.attractionTagRepository.saveAll(attractionTags);
            this.attractionRepository.saveAll(attractions);

            List<Tour> tours = new ArrayList<>();
            List<SubAttraction> subAttractions = new ArrayList<>();
            tours.add(Tour.builder().name("tourName1").description("tourDescription").logo("https://www.tourLogo.png").price(12).build());
            subAttractions.add(SubAttraction.builder().starttime("10:00").endtime("11:00").tour("tourName1").attractionToTour("attractionName1").build());
            subAttractions.add(SubAttraction.builder().starttime("11:00").endtime("12:00").tour("tourName1").attractionToTour("attractionName2").build());
            subAttractions.add(SubAttraction.builder().starttime("12:00").endtime("13:00").tour("tourName1").attractionToTour("attractionName3").build());

            tours.add(Tour.builder().name("tourName2").description("tourDescription").logo("https://www.tourLogo.png").price(12).build());
            subAttractions.add(SubAttraction.builder().starttime("10:00").endtime("11:00").tour("tourName2").attractionToTour("attractionName1").build());

            this.tourRepository.saveAll(tours);
            this.subAttractionRepository.saveAll(subAttractions);
        }
    }

    @Transactional
    public void createAttraction(ROInAttraction roInAttraction) {
        if (roInAttraction == null)
            throw new BadArgumentException("Attraction cannot be null");
        if (roInAttraction.getName() == null || roInAttraction.getName().isBlank())
            throw new BadArgumentException("Attraction name cannot be empty");
        if (this.attractionRepository.existsById(roInAttraction.getName()))
            throw new BadArgumentException("Attraction with name '" + roInAttraction.getName() + "' already exists");
        if (roInAttraction.getTags() == null)
            roInAttraction.setTags(new ArrayList<>());

        Attraction attraction = Attraction.builder()
                .name(roInAttraction.getName())
                .description(roInAttraction.getDescription())
                .logo(roInAttraction.getLogo())
                .build();
        this.attractionTagRepository.saveAll(roInAttraction.getTags().stream().map(tag -> AttractionTag.builder().attractionName(roInAttraction.getName()).tag(tag).build()).toList());
        this.attractionRepository.save(attraction);
    }

    @Transactional
    public void createTour(ROTour rotour) {
        if (rotour == null)
            throw new BadArgumentException("Tour cannot be null");
        if (rotour.getName() == null || rotour.getName().isBlank())
            throw new BadArgumentException("Tour name cannot be empty");
        if (this.tourRepository.existsById(rotour.getName()))
            throw new BadArgumentException("Tour with name '" + rotour.getName() + "' already exists");
        if (rotour.getAttractions() == null)
            rotour.setAttractions(new ArrayList<>());

        Tour tour = Tour.builder()
                .name(rotour.getName())
                .description(rotour.getDescription())
                .logo(rotour.getLogo())
                .price(rotour.getPrice())
                .build();
        List<SubAttraction> subAttractions = rotour.getAttractions().stream()
                .map(roSubAttraction -> SubAttraction.builder()
                        .starttime(roSubAttraction.getBegin())
                        .endtime(roSubAttraction.getEnd())
                        .tour(rotour.getName())
                        .attractionToTour(this.attractionRepository.findById(roSubAttraction.getAttraction()).orElseThrow(() ->
                                new BadArgumentException("Attraction with name " + roSubAttraction.getAttraction() + " does not exist")
                        ).getName()).build())
                .toList();
        this.tourRepository.save(tour);
        this.subAttractionRepository.saveAll(subAttractions);
    }

    @Transactional
    public void deleteAttraction(String name) {
        if (name == null || name.isBlank())
            throw new BadArgumentException("Name cannot be empty");
        if (!this.attractionRepository.existsById(name))
            throw new BadArgumentException("Attraction with name '" + name + "' does not exist");
        this.subAttractionRepository.deleteAllByAttractionToTour(name);
        this.attractionTagRepository.deleteAllByAttractionName(name);
        this.attractionRepository.deleteById(name);
    }

    @Transactional
    public void deleteTour(String name) {
        if (name == null || name.isBlank())
            throw new BadArgumentException("Name cannot be empty");
        if (!this.tourRepository.existsById(name))
            throw new BadArgumentException("Tour with name '" + name + "' does not exist");
        this.subAttractionRepository.deleteAllByTour(name);
        this.tourRepository.deleteById(name);
    }

    private String convertJSONToString(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new UnexpectedErrorException("Could not convert object to JSON");
        }
    }

    public void updateMetadata(ROMetadata roMetadata) {
        if (roMetadata == null)
            throw new BadArgumentException("Metadata cannot be null");
        this.metadataRepository.findAll().forEach(data -> {
            switch (data.getM_name()) {
                case "name":
                    if (Objects.equals(data.getM_value(), roMetadata.getName()) || roMetadata.getName() == null || roMetadata.getName().isBlank())
                        break;
                    data.setM_value(roMetadata.getName());
                    this.metadataRepository.save(data);
                    break;
                case "description":
                    if (Objects.equals(data.getM_value(), roMetadata.getDescription()) || roMetadata.getDescription() == null)
                        break;
                    data.setM_value(roMetadata.getDescription());
                    this.metadataRepository.save(data);
                    break;
                case "logo":
                    if (Objects.equals(data.getM_value(), roMetadata.getLogo()) || roMetadata.getLogo() == null)
                        break;
                    data.setM_value(roMetadata.getLogo());
                    this.metadataRepository.save(data);
                    break;
                case "address":
                    if (Objects.equals(data.getM_value(), roMetadata.getAddress()) || roMetadata.getAddress() == null)
                        break;
                    data.setM_value(roMetadata.getAddress());
                    this.metadataRepository.save(data);
                    break;
                case "prices":
                    String pricesString = this.convertJSONToString(roMetadata.getPrices());
                    if (Objects.equals(data.getM_value(), pricesString) || roMetadata.getPrices() == null)
                        break;
                    data.setM_value(pricesString);
                    this.metadataRepository.save(data);
                    break;
                case "openingHours":
                    String openingHoursString = this.convertJSONToString(roMetadata.getOpeningHours());
                    if (Objects.equals(data.getM_value(), openingHoursString) || roMetadata.getOpeningHours() == null)
                        break;
                    data.setM_value(openingHoursString);
                    this.metadataRepository.save(data);
                    break;
            }
        });
    }

    @Transactional
    public void updateAttraction(String name, ROInAttraction roattraction) {
        if (name.isBlank())
            throw new BadArgumentException("Name cannot be empty");
        if (roattraction == null)
            throw new BadArgumentException("Attraction cannot be null");
        if (roattraction.getName() == null || roattraction.getName().isBlank())
            throw new BadArgumentException("Attraction name cannot be empty");
        if (!this.attractionRepository.existsById(name))
            throw new BadArgumentException("Attraction with name '" + name + "' does not exist");
        if (roattraction.getTags() == null)
            roattraction.setTags(new ArrayList<>());
        for (String tag : roattraction.getTags()) {
            if (tag == null || tag.isBlank())
                throw new BadArgumentException("Tag cannot be empty");
        }

        Attraction attraction = Attraction.builder()
                .name(roattraction.getName())
                .description(roattraction.getDescription())
                .logo(roattraction.getLogo())
                .build();

        this.attractionTagRepository.deleteAllByAttractionName(name);
        this.attractionTagRepository.saveAll(roattraction.getTags().stream().map(tag -> AttractionTag.builder().attractionName(roattraction.getName()).tag(tag).build()).toList());
        if (!Objects.equals(name, roattraction.getName())) {
            this.attractionRepository.updateName(name, roattraction.getName());
            this.subAttractionRepository.updateAllByAttractionToTourName(name, roattraction.getName());
            this.attractionTagRepository.updateAllByAttractionName(name, roattraction.getName());
        }
        this.attractionRepository.save(attraction);
    }

    @Transactional
    public void updateTour(String name, ROTour rotour) {
        if (name.isBlank())
            throw new BadArgumentException("Name cannot be empty");
        if (!this.tourRepository.existsById(name))
            throw new BadArgumentException("Tour with name '" + name + "' does not exist");
        if (rotour == null)
            throw new BadArgumentException("Tour cannot be null");
        if (rotour.getName() == null || rotour.getName().isBlank())
            throw new BadArgumentException("Tour name cannot be empty");
        if (rotour.getAttractions() == null)
            rotour.setAttractions(new ArrayList<>());
        for (ROSubAttraction attraction : rotour.getAttractions()) {
            if (attraction.getAttraction() == null || attraction.getAttraction().isBlank())
                throw new BadArgumentException("Attraction name cannot be empty");
        }

        Tour tour = Tour.builder()
                .name(rotour.getName())
                .description(rotour.getDescription())
                .logo(rotour.getLogo())
                .build();

        this.subAttractionRepository.deleteAllByTour(name);
        List<SubAttraction> subAttractions = rotour.getAttractions().stream()
                .map(roSubAttraction -> SubAttraction.builder()
                        .starttime(roSubAttraction.getBegin())
                        .endtime(roSubAttraction.getEnd())
                        .tour(rotour.getName())
                        .attractionToTour(this.attractionRepository.findById(roSubAttraction.getAttraction()).orElseThrow(() ->
                                new BadArgumentException("Attraction with name " + roSubAttraction.getAttraction() + " does not exist")
                        ).getName()).build())
                .toList();

        if (!Objects.equals(name, rotour.getName())) {
            this.tourRepository.updateName(name, rotour.getName());
            this.subAttractionRepository.updateAllByAttractionToTourName(name, rotour.getName());
        }

        this.subAttractionRepository.saveAll(subAttractions);
        this.tourRepository.save(tour);
    }
}
