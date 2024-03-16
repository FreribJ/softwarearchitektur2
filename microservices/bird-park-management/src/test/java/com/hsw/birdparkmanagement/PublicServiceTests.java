package com.hsw.birdparkmanagement;

import com.hsw.birdparkmanagement.Exceptions.BadArgumentException;
import com.hsw.birdparkmanagement.model.database.Attraction;
import com.hsw.birdparkmanagement.model.database.Metadata;
import com.hsw.birdparkmanagement.model.database.SubAttraction;
import com.hsw.birdparkmanagement.model.database.Tour;
import com.hsw.birdparkmanagement.model.ui.ROAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.AttractionRepository;
import com.hsw.birdparkmanagement.repository.MetadataRepository;
import com.hsw.birdparkmanagement.repository.TourRepository;
import com.hsw.birdparkmanagement.service.PublicService;
import org.aspectj.lang.annotation.Before;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class PublicServiceTests {

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
    void testGetMetadata() {
        ROMetadata metadata = publicService.getMetadata();
        assertEquals("parkName", metadata.getName());
        assertEquals("parkDescription", metadata.getDescription());
        assertEquals("https://www.parkLogo.png/", metadata.getLogo());
        assertEquals("parkAddress", metadata.getAddress());
    }

    @Test
    void testGetTour() {
        ROTour tour = publicService.getTour("tourName1");
        assertEquals("tourName", tour.getName());
        assertEquals("https://www.tourLogo.png/", tour.getLogo());
        assertEquals(12, tour.getPrice());
        assertEquals(3, tour.getAttractions().size());
        assertEquals("attractionName1", tour.getAttractions().get(0).getAttraction());
    }

    @Test
    void testGetAttraction() {
        ROAttraction attraction = publicService.getAttraction("attractionName1");
        assertEquals("attractionName1", attraction.getName());
        assertEquals("https://www.attractionLogo.png/", attraction.getLogo());
        assertEquals("attractionDescription", attraction.getDescription());
        assertEquals("Tag1", attraction.getTags().get(0));
    }

    @Test
    void testGetInvalidTour() {
        assertThrows(BadArgumentException.class, () -> publicService.getTour(""));
        assertThrows(BadArgumentException.class, () -> publicService.getTour(null));
    }

    @Test
    void testGetInvalidAttraction() {
        assertThrows(BadArgumentException.class, () -> publicService.getAttraction(""));
        assertThrows(BadArgumentException.class, () -> publicService.getAttraction(null));
    }
}
