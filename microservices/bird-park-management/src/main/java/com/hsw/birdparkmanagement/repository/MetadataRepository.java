package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.Metadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface MetadataRepository extends CrudRepository<Metadata, String> {
}
