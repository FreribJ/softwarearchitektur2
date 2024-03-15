package com.hsw.birdparkmanagement;

import com.hsw.birdparkmanagement.service.PublicService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServiceTests {

    @Autowired
    PublicService publicService;

    @Test
    void testGetMetadata() {
        assertEquals("Bird Park", publicService.getMetadata().getName());

    }
}
