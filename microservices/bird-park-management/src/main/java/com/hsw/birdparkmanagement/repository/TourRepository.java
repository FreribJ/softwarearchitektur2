package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.database.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, String> {

    @Query(value = "select name from tour", nativeQuery = true)
    List<String> getTourNames();


    @Modifying
    @Query(value = "update Tour t set t.name = ?2 where t.name = ?1")
    void updateName(String oldName, String newName);
}
