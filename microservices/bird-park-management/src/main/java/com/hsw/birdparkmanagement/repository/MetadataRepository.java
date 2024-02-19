package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.database.Metadata;
import org.springframework.data.repository.CrudRepository;


public interface MetadataRepository extends CrudRepository<Metadata, String> {
}
