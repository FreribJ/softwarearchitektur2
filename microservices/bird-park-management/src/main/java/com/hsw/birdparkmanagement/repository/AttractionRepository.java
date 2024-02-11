package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.Attraction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttractionRepository extends CrudRepository<Attraction, String> {

//    @Query(value = "select name from attraction", nativeQuery = true)
//    public List<String> getAttractionNames();

}
