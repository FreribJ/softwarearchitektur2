package com.hsw.birdparkgeneral.api.spi;

import com.hsw.birdparkgeneral.model.ui.ROAttraction;
import com.hsw.birdparkgeneral.model.ui.ROMetadata;
import com.hsw.birdparkgeneral.model.ui.ROTour;
import com.hsw.birdparkgeneral.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ManagmentSPI {

    final String host = "http://localhost:8080/";
    RestTemplate restTemplate = new RestTemplate();


    //Metadata
    public ROMetadata getMetadata() {
        return this.restTemplate.getForObject(host + "metadata", ROMetadata.class);
    }

    //Attraction
    public List<ROAttraction> getRoAttractions() {
        return this.restTemplate.getForObject(host + "attractions", List.class);
    }

    public ROAttraction getAttraction(String name) {
        return this.restTemplate.getForObject(host + "attraction/" + name, ROAttraction.class);
    }

    public List<String> attractionNames() {
        return this.restTemplate.getForObject(host + "attractionNames", List.class);
    }

    //Tour
    public List<ROTour> tours() {
        return this.restTemplate.getForObject(host + "tours", List.class);
    }

    public ROTour tour(@PathVariable String name) {
        return this.restTemplate.getForObject(host + "tour/" + name, ROTour.class);
    }

    public List<String> tourNames() {
        return this.restTemplate.getForObject(host + "tourNames", List.class);
    }
}
