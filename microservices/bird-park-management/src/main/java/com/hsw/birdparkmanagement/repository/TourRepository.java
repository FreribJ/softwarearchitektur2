package com.hsw.birdparkmanagement.repository;

import com.hsw.birdparkmanagement.model.Tour;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, String> {
}
