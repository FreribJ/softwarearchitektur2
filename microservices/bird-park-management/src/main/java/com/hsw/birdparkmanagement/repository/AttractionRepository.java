package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.database.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, String> {

    @Query(value = "select name from attraction", nativeQuery = true)
    List<String> getAttractionNames();


    @Query(value = "update attraction set name = ?2 where name = ?1", nativeQuery = true)
    void updateName(String oldName, String newName);

    @Query(value = "select nearest_tour_name from attraction_nearest_tour where attraction_name = ?1" , nativeQuery = true)
    List<String> getNearestTourNames(String attractionName);

}
