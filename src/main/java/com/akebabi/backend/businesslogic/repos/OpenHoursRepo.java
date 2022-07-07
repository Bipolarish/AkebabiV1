package com.akebabi.backend.businesslogic.repos;

import com.akebabi.backend.businesslogic.entities.OpenHours;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenHoursRepo extends CrudRepository<OpenHours, Integer> {
}
