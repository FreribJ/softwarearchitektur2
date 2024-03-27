package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.database.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, String> {

    @Query(value = "select name from attraction", nativeQuery = true)
    List<String> getAttractionNames();

    @Modifying
    @Query(value = "update attraction set name = ?2 where name = ?1", nativeQuery = true)
    void updateName(String oldName, String newName);
}
