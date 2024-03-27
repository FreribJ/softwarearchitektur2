package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.database.AttractionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionTagRepository extends JpaRepository<AttractionTag, Long> {

    List<AttractionTag> findAllByAttractionName(String attractionName);


    void deleteAllByAttractionName(String attractionName);

    @Modifying
    @Query("update AttractionTag a set a.attractionName = ?2 where a.attractionName = ?1")
    void updateAllByAttractionName(String attractionName, String newAttractionName);

}
