package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.database.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, String> {

    @Query(value = "select name from tour", nativeQuery = true)
    List<String> getTourNames();

    @Query(value = "update tour set name = ?2 where name = ?1", nativeQuery = true)
    void updateName(String oldName, String newName);

//    @Query(value = "select attractions_name from tour_attractions where tour_name = ?1" , nativeQuery = true)
//    List<String> getAttractions(String tourName);

    @Query(value = "select tsa.tour_name from tour_sub_attractions tsa join sub_attraction sa on sa.id = tsa.sub_attractions_id where sa.attraction_name = ?1 ", nativeQuery = true)
    List<String> findAllByAttractionName(String attractionName);

}
