package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.database.SubAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubAttractionRepository extends JpaRepository<SubAttraction, Long> {

    List<SubAttraction> findAllByAttractionToTour(String attractionName);

    List<SubAttraction> findAllByTour(String tourName);

    void deleteAllByTour(String tourName);

    void deleteAllByAttractionToTour(String attractionName);

    @Modifying
    @Query("update SubAttraction s set s.attractionToTour = ?2 where s.attractionToTour = ?1")
    void updateAllByAttractionToTourName(String attractionName, String newAttractionName);

    @Modifying
    @Query("update SubAttraction s set s.tour = ?2 where s.tour = ?1")
    void updateAllByTourName(String tourName, String newTourName);
}
