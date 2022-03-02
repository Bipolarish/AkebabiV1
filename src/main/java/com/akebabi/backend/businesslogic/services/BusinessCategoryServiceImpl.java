package com.akebabi.backend.businesslogic.services;

import com.akebabi.backend.businesslogic.entities.BusinessCategory;
import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.repos.BusinessAccountRepo;
import com.akebabi.backend.businesslogic.repos.BusinessCategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusinessCategoryServiceImpl implements BusinessCategoryService{

    private final BusinessCategoryRepo businessCategoryRepo;
    private final BusinessAccountRepo businessAccountRepo;
    private final BusinessAccountService businessAccountService;

    @Override
    public BusinessCategory save(BusinessCategory category) {
        return businessCategoryRepo.save(category);
    }

    @Override
    public List<BusinessCategory> getCategories() {
        return (List<BusinessCategory>) businessCategoryRepo.findAll();
    }

    @Override
    public BusinessCategory getCategory(Integer id) {
        Optional<BusinessCategory> businessCategoryById = businessCategoryRepo.findById(id);
        return businessCategoryById.orElseThrow(()-> new RuntimeException("Unavailable"));
    }

    @Override
    public BusinessInfo updateBusinessCategory(String businessPublicId, Integer categoryId) {
        BusinessCategory category = businessCategoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Couldnt find a category with ID provided"));
        BusinessInfo existingBusinessInfo = businessAccountService.getBusinessByBusinessPublicId(businessPublicId);
        existingBusinessInfo.setCategory(category);
        return businessAccountRepo.save(existingBusinessInfo);
    }

    @Override
    public List<BusinessInfo> getBusinessesByCategory(Integer categoryId) {
        BusinessCategory businessByCategory = getCategory(categoryId);
        if(businessByCategory == null) {
            throw new RuntimeException("Category not found");
        }
        return businessAccountRepo.findAllByCategory(businessByCategory);
    }


}
