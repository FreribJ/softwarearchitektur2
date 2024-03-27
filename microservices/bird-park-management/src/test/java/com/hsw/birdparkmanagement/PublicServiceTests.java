package com.hsw.birdparkmanagement;

import com.hsw.birdparkmanagement.Exceptions.BadArgumentException;
import com.hsw.birdparkmanagement.model.database.*;
import com.hsw.birdparkmanagement.model.ui.ROOutAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.*;
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
public class PublicServiceTests {

    @Autowired
    private PublicService publicService;

    @BeforeAll
    static void setup(@Autowired MetadataRepository metadataRepository, @Autowired AttractionRepository attractionRepository, @Autowired TourRepository tourRepository, @Autowired AttractionTagRepository attractionTagRepository, @Autowired SubAttractionRepository subAttractionRepository) {
        List<Metadata> metadata = new ArrayList<>();
        metadata.add(Metadata.builder().m_name("name").m_type("String").m_value("parkName").build());
        metadata.add(Metadata.builder().m_name("description").m_type("String").m_value("parkDescription").build());
        metadata.add(Metadata.builder().m_name("logo").m_type("String").m_value("https://www.parkLogo.png/").build());
        metadata.add(Metadata.builder().m_name("address").m_type("String").m_value("parkAddress").build());
        metadata.add(Metadata.builder().m_name("prices").m_type("JSON").m_value("[{\"category\":\"Adult\",\"price\":20.0},{\"category\":\"Child\",\"price\":10.0}]").build());
        metadata.add(Metadata.builder().m_name("openingHours").m_type("JSON").m_value("[{\"weekday\":\"Wochentag\",\"hours\":\"9:00 - 18:00\",\"info\":\"Außer an Feiertagen\"},{\"weekday\":\"Wochenende\",\"hours\":\"9:00 - 18:00\",\"info\":\"\"}]").build());

        metadataRepository.saveAll(metadata);

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

        attractionTagRepository.saveAll(attractionTags);
        attractionRepository.saveAll(attractions);

        List<Tour> tours = new ArrayList<>();
        List<SubAttraction> subAttractions = new ArrayList<>();
        tours.add(Tour.builder().name("tourName1").description("tourDescription").logo("https://www.tourLogo.png").price(12).build());
        subAttractions.add(SubAttraction.builder().starttime("10:00").endtime("11:00").tour("tourName1").attractionToTour("attractionName1").build());
        subAttractions.add(SubAttraction.builder().starttime("11:00").endtime("12:00").tour("tourName1").attractionToTour("attractionName2").build());
        subAttractions.add(SubAttraction.builder().starttime("12:00").endtime("13:00").tour("tourName1").attractionToTour("attractionName3").build());

        tours.add(Tour.builder().name("tourName2").description("tourDescription").logo("https://www.tourLogo.png").price(12).build());
        subAttractions.add(SubAttraction.builder().starttime("10:00").endtime("11:00").tour("tourName2").attractionToTour("attractionName1").build());

        tourRepository.saveAll(tours);
        subAttractionRepository.saveAll(subAttractions);
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
        assertEquals("tourName1", tour.getName());
        assertEquals("https://www.tourLogo.png", tour.getLogo());
        assertEquals(12, tour.getPrice());
        assertEquals(6, tour.getAttractions().size());
        assertEquals("attractionName1", tour.getAttractions().get(0).getAttraction());
    }

    @Test
    void testGetAttraction() {
        ROOutAttraction attraction = publicService.getAttraction("attractionName1");
        assertEquals("attractionName1", attraction.getName());
        assertEquals("https://www.attractionLogo.png", attraction.getLogo());
        assertEquals("attractionDescription", attraction.getDescription());
        assertEquals("tag1", attraction.getTags().get(0));
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
