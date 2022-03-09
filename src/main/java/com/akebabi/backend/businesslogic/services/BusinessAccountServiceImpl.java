package com.akebabi.backend.businesslogic.services;

import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.entities.BusinessCategory;
import com.akebabi.backend.businesslogic.entities.SocialLink;
import com.akebabi.backend.businesslogic.exception.BadRequestException;
import com.akebabi.backend.businesslogic.repos.BusinessAccountRepo;
import com.akebabi.backend.businesslogic.repos.BusinessCategoryRepo;
import com.akebabi.backend.businesslogic.repos.SocialLinksRepo;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BusinessAccountServiceImpl implements BusinessAccountService{

    private final BusinessAccountRepo businessAccountRepo;
    private final BusinessCategoryRepo businessCategoryRepo;
    private final SocialLinksRepo socialLinksRepo;
//    private final BusinessCategoryService businessCategoryService;
    private final UserRepo userRepo;

    @Override
    public Map<String, String> isBusinessValid(BusinessInfo businessDetail, String pid) {
        Map<String, String> errors = new HashMap<>();
        List<BusinessInfo> existingBusinessByProfileUrlName = businessAccountRepo.findAllByProfileUrlNameIgnoreCase(businessDetail.getProfileUrlName());
        List<BusinessInfo> existingBusinessByEmail = businessAccountRepo.findAllByEmailIgnoreCase(businessDetail.getEmail());
        List<BusinessInfo> existingBusinessByPhone = businessAccountRepo.findAllByPrimaryPhoneIgnoreCase(businessDetail.getPrimaryPhone());
        //new save check
        if(businessDetail.getId() == null ){
            if(!existingBusinessByProfileUrlName.isEmpty() ) {
                errors.put("Profile_Name_Check","Profile Url Name is used");
            }

            if(!existingBusinessByEmail.isEmpty()) {
                errors.put("Email_Check","Email is used");
            }
            if(!existingBusinessByPhone.isEmpty()) {
                errors.put("Phone_Check","Phone is used");
            }
        } else {
//            if(!existingBusinessByProfileUrlName.isEmpty()) {
//                boolean profileNameNoMatchFound = existingBusinessByProfileUrlName.stream().map(businessInfo -> businessInfo.getPostedBy().getUserPublicId()).noneMatch(expid -> expid.equalsIgnoreCase(pid));
//                if(profileNameNoMatchFound) {
//                    errors.put("Profile_Name_Check","Profile Url Name is used");
//                }
//            }
//            if(!existingBusinessByEmail.isEmpty()) {
//                boolean emailNoMatchFound = existingBusinessByEmail.stream().map(businessInfo -> businessInfo.getPostedBy().getUserPublicId()).noneMatch(expid -> expid.equalsIgnoreCase(pid));
//                if(emailNoMatchFound) {
//                    errors.put("Email_Check","Email is used");
//                }
//            }
//            if(!existingBusinessByPhone.isEmpty()) {
//                boolean phoneNoMatchFound = existingBusinessByPhone.stream().map(businessInfo -> businessInfo.getPostedBy().getUserPublicId()).noneMatch(expid -> expid.equalsIgnoreCase(pid));
//                if(phoneNoMatchFound) {
//                    errors.put("Phone_Check","Phone is used");
//                }
//            }

//            businessAccountRepo.findAllByPostedBy()
        }

        return errors;
    }



    @Override
    public BusinessInfo save(BusinessInfo business, Integer categoryId, String posterPublicId) {
//        List<SocialLink> savedSocialLinks = new ArrayList<>();
//        if(business.getSocialLinks()!=null && !business.getSocialLinks().isEmpty()) {
//            for (SocialLink socialLink : business.getSocialLinks()) {
//                socialLinksRepo.save(socialLink);
//            }
//        }


        Optional<BusinessCategory> categoryById = businessCategoryRepo.findById(categoryId);
        if(!categoryById.isPresent()) {
            throw new BadRequestException("Category can not be found");
        }
        BusinessCategory category = categoryById.get();
        business.setCategory(category);
        Optional<User> existingUserOptional = userRepo.findByUserPublicId(posterPublicId);
        if(!existingUserOptional.isPresent()) {
            throw new BadRequestException("User can not be found");
        }
        User existingUser = existingUserOptional.get();
        business.setPostedBy(existingUser);
        business.setBusinessPublicId(UUID.randomUUID().toString());
        return businessAccountRepo.save(business);
    }

    @Override
    public BusinessInfo updateBusinessProfile(BusinessInfo businessDetail) {
        if(businessDetail == null ||businessDetail.getId() == null && businessDetail.getBusinessPublicId() == null) {
            throw new BadRequestException("Business Public Id required");
        }

        List<BusinessInfo> businessInfoById = businessAccountRepo.findAllByBusinessPublicId(businessDetail.getBusinessPublicId());
        if(businessInfoById==null || businessInfoById.isEmpty()) {
            throw new RuntimeException("Couldnt find business by id");
        }
        BusinessInfo existingBusiness = businessInfoById.get(0);
        existingBusiness.setCity(businessDetail.getCity());
        existingBusiness.setDescription(businessDetail.getDescription());
        existingBusiness.setName(businessDetail.getName());
        existingBusiness.setEmail(businessDetail.getEmail());
        existingBusiness.setStreetAddress(businessDetail.getStreetAddress());
        existingBusiness.setPrimaryPhone(businessDetail.getPrimaryPhone());
        existingBusiness.setPobox(businessDetail.getPobox());
        existingBusiness.setLatitude(businessDetail.getLatitude());
        existingBusiness.setLongitude(businessDetail.getLongitude());
        existingBusiness.setWebsite(businessDetail.getWebsite());
        return businessAccountRepo.save(existingBusiness);
    }

    @Override
    public boolean delete(String businessPublicId) {
        if(businessPublicId == null) {
            throw new BadRequestException("Business Public Id required");
        }
        List<BusinessInfo> businessInfos = businessAccountRepo.findAllByBusinessPublicId(businessPublicId);
        if( businessInfos == null || businessInfos.isEmpty()) {
            throw new RuntimeException("Business with the Public ID not found");
        }
        businessAccountRepo.delete(businessInfos.get(0));
        return true;
    }

    @Override
    public Page<BusinessInfo> getBusinessesList(Pageable pageable) {
        return businessAccountRepo.findAll(pageable);
    }

    @Override
    public Page<BusinessInfo> getBusinessesList(String filterKey, Pageable pageable) {
        return businessAccountRepo.findAllByNameContainsIgnoreCase(filterKey, pageable);
    }

    @Override
    public Page<BusinessInfo> getBusinessesList(Integer categoryId, Pageable pageable) {
//        BusinessCategory category = businessCategoryService.getCategory(categoryId);
        BusinessCategory category = businessCategoryRepo
                .findById(categoryId).orElseThrow(()-> new BadRequestException("Couldnt find category by category ID"));
        if(category == null) {
            throw new RuntimeException("Category not found");
        }
        return businessAccountRepo.findAllByCategory(category, pageable);
    }

    @Override
    public List<BusinessInfo> userBusinessesList(String userPublicId) {
        if(userPublicId == null) {
            throw new BadRequestException("User Public Id required");
        }
        Optional<User> userByUserPublicId = userRepo.findByUserPublicId(userPublicId);
        if (!userByUserPublicId.isPresent()) {
            throw new BadRequestException("User with the Public Id is not found");
        }
        User existingUser =  userByUserPublicId.get();
        if(existingUser == null) {
            throw new RuntimeException("Category not found");
        }
        return businessAccountRepo.findAllByPostedBy(existingUser);
    }

    @Override
    public BusinessInfo getBusinessByBusinessPublicId(String businessPublicId) {
        List<BusinessInfo> businessInfoList = businessAccountRepo.findAllByBusinessPublicId(businessPublicId);
        if(businessInfoList == null || businessInfoList.isEmpty()) {
            return null;
        }
        return businessInfoList.get(0);
    }
}
