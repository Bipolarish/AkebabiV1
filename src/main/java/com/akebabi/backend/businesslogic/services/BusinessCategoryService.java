package com.akebabi.backend.businesslogic.services;

import com.akebabi.backend.businesslogic.entities.BusinessCategory;
import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusinessCategoryService {

    BusinessCategory save(BusinessCategory category);
    List<BusinessCategory> getCategories();
    BusinessCategory getCategory(Integer id);
    BusinessInfo updateBusinessCategory(String businessPublicId, Integer Id);
    List<BusinessInfo> getBusinessesByCategory(Integer categoryId);



}
