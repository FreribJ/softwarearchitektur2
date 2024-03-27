package com.hsw.birdparkmanagement;

import com.hsw.birdparkmanagement.Exceptions.BadArgumentException;
import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Metadata;
import com.hsw.birdparkmanagement.model.database.SubAttraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROInAttraction;
import com.hsw.birdparkmanagement.model.ui.ROOutAttraction;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import com.hsw.birdparkmanagement.service.PrivateService;
import com.hsw.birdparkmanagement.service.PublicService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class PrivateServiceTests {

    @Autowired
    private PrivateService privateService;

    @Autowired
    private PublicService publicService;

    @BeforeAll
    static void setup(@Autowired MetadataRepository metadataRepository, @Autowired AttractionRepository attractionRepository, @Autowired TourRepository tourRepository) {
        List<Metadata> metadata = new ArrayList<>();
        metadata.add(Metadata.builder().m_name("name").m_type("String").m_value("parkName").build());
        metadata.add(Metadata.builder().m_name("description").m_type("String").m_value("parkDescription").build());
        metadata.add(Metadata.builder().m_name("logo").m_type("String").m_value("https://www.parkLogo.png/").build());
        metadata.add(Metadata.builder().m_name("address").m_type("String").m_value("parkAddress").build());
        metadata.add(Metadata.builder().m_name("prices").m_type("JSON").m_value("[{\"category\":\"Adult\",\"price\":20.0},{\"category\":\"Child\",\"price\":10.0}]").build());
        metadata.add(Metadata.builder().m_name("openingHours").m_type("JSON").m_value("[{\"weekday\":\"Wochentag\",\"hours\":\"9:00 - 18:00\",\"info\":\"Außer an Feiertagen\"},{\"weekday\":\"Wochenende\",\"hours\":\"9:00 - 18:00\",\"info\":\"\"}]").build());

        metadataRepository.saveAll(metadata);

        List<Attraction> attractions = new ArrayList<>();
        attractions.add(Attraction.builder().name("attractionName1").description("attractionDescription").tags(List.of("Tag1", "Tag2", "Tag3")).logo("https://www.attractionLogo.png/").build());
        attractions.add(Attraction.builder().name("attractionName2").description("attractionDescription").tags(List.of("Tag1", "Tag2", "Tag3")).logo("https://www.attractionLogo.png/").build());
        attractions.add(Attraction.builder().name("attractionName3").description("attractionDescription").tags(List.of("Tag1", "Tag2", "Tag3")).logo("https://www.attractionLogo.png/").build());

        attractionRepository.saveAll(attractions);

        List<Tour> tours = new ArrayList<>();
        tours.add(Tour.builder().name("tourName1").description("tourDescription").logo("https://www.tourLogo.png/").price(12).subAttractions(List.of(
                SubAttraction.builder().attraction(attractions.get(0)).starttime("9:00").endtime("10:00").build(),
                SubAttraction.builder().attraction(attractions.get(1)).starttime("10:00").endtime("11:00").build(),
                SubAttraction.builder().attraction(attractions.get(2)).starttime("11:00").endtime("12:00").build()
        )).build());

        tours.add(Tour.builder().name("tourName2").description("tourDescription").logo("https://www.tourLogo.png/").price(12).subAttractions(List.of(
        )).build());

        tourRepository.saveAll(tours);
    }
    @Test
    void testCreateAttraction() {
        assertThrows(BadArgumentException.class, () -> privateService.createAttraction(null));
        assertThrows(BadArgumentException.class, () -> privateService.createAttraction(ROInAttraction.builder().name("").build()));
        assertThrows(BadArgumentException.class, () -> privateService.createAttraction(ROInAttraction.builder().name("alreadyTakenName").build()));

        ROInAttraction newAttraction = ROInAttraction.builder().name("newAttraction").build();
        privateService.createAttraction(newAttraction);
        assertEquals(publicService.getAttraction("newAttraction"), newAttraction);
    }

    @Test
    void testCreateTour() {
        assertThrows(BadArgumentException.class, () -> privateService.createTour(null));
        assertThrows(BadArgumentException.class, () -> privateService.createTour(ROTour.builder().name("").build()));
        assertThrows(BadArgumentException.class, () -> privateService.createTour(ROTour.builder().name("alreadyTakenName").build()));

        ROTour newTour = ROTour.builder().name("newTour").build();
        privateService.createTour(newTour);
        assertEquals(publicService.getTour("newTour"), newTour);
    }

    @Test
    void testDeleteAttraction() {
        assertThrows(BadArgumentException.class, () -> privateService.deleteAttraction(null));
        assertThrows(BadArgumentException.class, () -> privateService.deleteAttraction(""));

        privateService.deleteAttraction("attractionName1");
        assertThrows(BadArgumentException.class, () -> privateService.deleteAttraction("attractionName1"));
    }

    @Test
    void testDeleteTour() {
        assertThrows(BadArgumentException.class, () -> privateService.deleteTour(null));
        assertThrows(BadArgumentException.class, () -> privateService.deleteTour(""));

        privateService.deleteTour("tourName1");
        assertThrows(BadArgumentException.class, () -> privateService.deleteTour("tourName1"));
    }

    @Test
    void testUpdateMetadata() {
        //TODO: Implement
    }

    @Test
    void testUpdateAttraction() {
        //TODO: Implement
    }
}
