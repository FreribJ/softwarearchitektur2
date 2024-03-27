package com.hsw.birdparkmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsw.birdparkmanagement.Exceptions.BadArgumentException;
import com.hsw.birdparkmanagement.Exceptions.UnexpectedErrorException;
import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Metadata;
import com.hsw.birdparkmanagement.model.database.SubAttraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROInAttraction;
import com.hsw.birdparkmanagement.model.ui.ROOutAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            List<Metadata> metadata = new ArrayList<>();
            metadata.add(Metadata.builder().m_name("name").m_type("String").m_value("Bird Park HSW").build());
            metadata.add(Metadata.builder().m_name("description").m_type("String").m_value("Welcome to the Bird Park").build());
            metadata.add(Metadata.builder().m_name("logo").m_type("String").m_value("https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Hochschule_Weserbergland_logo.svg/2560px-Hochschule_Weserbergland_logo.svg.png").build());
            metadata.add(Metadata.builder().m_name("address").m_type("String").m_value("Am Stockhof 2, 31785 Hameln").build());
            metadata.add(Metadata.builder().m_name("prices").m_type("JSON").m_value("[{\"category\":\"Adult\",\"price\":20.0},{\"category\":\"Child\",\"price\":10.0}]").build());
            metadata.add(Metadata.builder().m_name("openingHours").m_type("JSON").m_value("[{\"weekday\":\"Wochentag\",\"hours\":\"9:00 - 18:00\",\"info\":\"Außer an Feiertagen\"},{\"weekday\":\"Wochenende\",\"hours\":\"9:00 - 18:00\",\"info\":\"\"}]").build());

            this.metadataRepository.saveAll(metadata);

            List<Attraction> attractions = new ArrayList<>();
            attractions.add(Attraction.builder().name("attractionName1").description("attractionDescription").tags(List.of("Tag1", "Tag2", "Tag3")).logo("https://www.attractionLogo.png").build());
            attractions.add(Attraction.builder().name("attractionName2").description("attractionDescription").tags(List.of("Tag1", "Tag2", "Tag3")).logo("https://www.attractionLogo.png").build());
            attractions.add(Attraction.builder().name("attractionName3").description("attractionDescription").tags(List.of("Tag1", "Tag2", "Tag3")).logo("https://www.attractionLogo.png").build());

            attractionRepository.saveAll(attractions);

            List<Tour> tours = new ArrayList<>();
            tours.add(Tour.builder().name("tourName1").description("tourDescription").logo("https://www.tourLogo.png").price(12).subAttractions(List.of(
                    SubAttraction.builder().attraction(attractions.get(0)).starttime("9:00").endtime("10:00").build(),
                    SubAttraction.builder().attraction(attractions.get(1)).starttime("10:00").endtime("11:00").build(),
                    SubAttraction.builder().attraction(attractions.get(2)).starttime("11:00").endtime("12:00").build()
            )).build());

            tours.add(Tour.builder().name("tourName2").description("tourDescription").logo("https://www.tourLogo.png").price(12).subAttractions(List.of(
            )).build());

            tourRepository.saveAll(tours);
        }
    }

    public void createAttraction(ROInAttraction roInAttraction) {
        if(roInAttraction.getName() == null || roInAttraction.getName().isEmpty())
            throw new BadArgumentException("Attraction name cannot be empty");
        if (this.attractionRepository.existsById(roInAttraction.getName()))
            throw new BadArgumentException("Attraction with name '" + roInAttraction.getName() + "' already exists");
        Attraction attraction = Attraction.builder()
                .name(roInAttraction.getName())
                .description(roInAttraction.getDescription())
                .logo(roInAttraction.getLogo())
                .tags(roInAttraction.getTags())
                .build();
        this.attractionRepository.save(attraction);
    }

    public void createTour(ROTour rotour) {
        if(rotour.getName() == null || rotour.getName().isEmpty())
            throw new BadArgumentException("Tour name cannot be empty");
        if (this.tourRepository.existsById(rotour.getName()))
            throw new BadArgumentException("Tour with name '" + rotour.getName() + "' already exists");

        Tour tour = Tour.builder()
                .name(rotour.getName())
                .description(rotour.getDescription())
                .logo(rotour.getLogo())
                .subAttractions(rotour.getAttractions().stream()
                        .map(roSubAttraction -> SubAttraction.builder()
                                .starttime(roSubAttraction.getBegin())
                                .endtime(roSubAttraction.getEnd())
                                .attraction(this.attractionRepository.findById(roSubAttraction.getAttraction()).orElseThrow(() ->
                                        new IllegalStateException("Attraction with name '" + roSubAttraction.getAttraction() + "' does not exist")
                                )).build())
                        .collect(Collectors.toList()))
                .build();
        this.tourRepository.save(tour);
    }

    //TODO: Abhängigkeiten lassen sich nicht löschen!
    public void deleteAttraction(String name) {
        if (!this.attractionRepository.existsById(name))
            throw new BadArgumentException("Attraction with name '" + name + "' does not exist");
        this.attractionRepository.deleteById(name);
    }

    public void deleteTour(String name) {
        if (!this.tourRepository.existsById(name))
            throw new BadArgumentException("Tour with name '" + name + "' does not exist");
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
                    if (Objects.equals(data.getM_value(), roMetadata.getName()) || roMetadata.getName() == null || roMetadata.getName().isEmpty())
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

    //TODO: abhängige Attraktionen auch aktualisieren
    @Transactional
    public void updateAttraction(String name, ROInAttraction roattraction) {
        if (name.isEmpty())
            throw new BadArgumentException("Name cannot be empty");
        if (roattraction == null)
            throw new BadArgumentException("Attraction cannot be null");
        if (roattraction.getName() == null || roattraction.getName().isEmpty())
            throw new BadArgumentException("Attraction name cannot be empty");
        if (!this.attractionRepository.existsById(name))
            throw new BadArgumentException("Attraction with name '" + name + "' does not exist");

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
            throw new BadArgumentException("Tour with name '" + name + "' does not exist");

        Tour tour = Tour.builder()
                .name(rotour.getName())
                .description(rotour.getDescription())
                .logo(rotour.getLogo())
                .subAttractions(rotour.getAttractions().stream()
                        .map(roSubAttraction -> SubAttraction.builder()
                                .starttime(roSubAttraction.getBegin())
                                .endtime(roSubAttraction.getEnd())
                                .attraction(this.attractionRepository.findById(roSubAttraction.getAttraction()).orElseThrow(() ->
                                        new BadArgumentException("Attraction with name " + roSubAttraction.getAttraction() + " does not exist")
                                )).build())
                        .collect(Collectors.toList()))
                .build();

        if (!Objects.equals(name, rotour.getName()))
            this.tourRepository.updateName(name, rotour.getName());
        this.tourRepository.save(tour);
    }
}
