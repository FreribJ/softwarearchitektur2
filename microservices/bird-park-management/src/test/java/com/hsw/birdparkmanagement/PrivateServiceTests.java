package com.hsw.birdparkmanagement;

import com.hsw.birdparkmanagement.Exceptions.BadArgumentException;
import com.hsw.birdparkmanagement.model.database.*;
import com.hsw.birdparkmanagement.model.ui.ROInAttraction;
import com.hsw.birdparkmanagement.model.ui.ROMetadata;
import com.hsw.birdparkmanagement.model.ui.ROOutAttraction;
import com.hsw.birdparkmanagement.model.ui.ROTour;
import com.hsw.birdparkmanagement.repository.*;
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
    static void setup(@Autowired MetadataRepository metadataRepository, @Autowired AttractionRepository attractionRepository, @Autowired TourRepository tourRepository, @Autowired SubAttractionRepository subAttractionRepository, @Autowired AttractionTagRepository attractionTagRepository) {
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
    void testCreateAttraction() {
        assertThrows(BadArgumentException.class, () -> privateService.createAttraction(null));
        assertThrows(BadArgumentException.class, () -> privateService.createAttraction(ROInAttraction.builder().name("").build()));
        assertThrows(BadArgumentException.class, () -> privateService.createAttraction(ROInAttraction.builder().name("attractionName1").build()));


        ROInAttraction newAttraction = ROInAttraction.builder().name("newAttraction").description("newAttractionDescription").logo("newAttractionLogo").build();
        privateService.createAttraction(newAttraction);
        assertEquals("newAttraction", publicService.getAttraction("newAttraction").getName());
        assertEquals("newAttractionDescription", publicService.getAttraction("newAttraction").getDescription());
        assertEquals("newAttractionLogo", publicService.getAttraction("newAttraction").getLogo());
        assertEquals(0, publicService.getAttraction("newAttraction").getTags().size());
    }

    @Test
    void testCreateTour() {
        assertThrows(BadArgumentException.class, () -> privateService.createTour(null));
        assertThrows(BadArgumentException.class, () -> privateService.createTour(ROTour.builder().name("").build()));
        assertThrows(BadArgumentException.class, () -> privateService.createTour(ROTour.builder().name("tourName1").build()));

        ROTour newTour = ROTour.builder().name("newTour").description("newTourDescription").logo("newTourLogo").price(12).build();
        privateService.createTour(newTour);
        assertEquals("newTour", publicService.getTour("newTour").getName());
        assertEquals("newTourDescription", publicService.getTour("newTour").getDescription());
        assertEquals("newTourLogo", publicService.getTour("newTour").getLogo());
        assertEquals(12, publicService.getTour("newTour").getPrice());
        assertEquals(0, publicService.getTour("newTour").getAttractions().size());
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
        assertEquals("parkName", publicService.getMetadata().getName());
        ROMetadata roMetadata = publicService.getMetadata();
        roMetadata.setName("newParkName");
        roMetadata.setDescription("newParkDescription");
        roMetadata.setLogo("newParkLogo");
        roMetadata.setAddress("newParkAddress");
        privateService.updateMetadata(roMetadata);
        assertEquals("newParkName", publicService.getMetadata().getName());
        assertEquals("newParkDescription", publicService.getMetadata().getDescription());
        assertEquals("newParkLogo", publicService.getMetadata().getLogo());
        assertEquals("newParkAddress", publicService.getMetadata().getAddress());
    }

    @Test
    void testUpdateAttraction() {
        ROOutAttraction roOutAttraction = publicService.getAttraction("attractionName1");
        ROInAttraction roInAttraction = new ROInAttraction();
        roInAttraction.setName("newAttractionName");
        roInAttraction.setDescription("newAttractionDescription");
        roInAttraction.setLogo("newAttractionLogo");
        privateService.updateAttraction("attractionName1", roInAttraction);
        assertEquals("newAttractionName", publicService.getAttraction("newAttractionName").getName());
        assertEquals("newAttractionDescription", publicService.getAttraction("newAttractionName").getDescription());
        assertEquals("newAttractionLogo", publicService.getAttraction("newAttractionName").getLogo());
    }
}
