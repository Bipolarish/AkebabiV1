package com.akebabi.backend.businesslogic.repos;

import com.akebabi.backend.businesslogic.entities.BusinessCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCategoryRepo extends CrudRepository<BusinessCategory, Integer> {


}
