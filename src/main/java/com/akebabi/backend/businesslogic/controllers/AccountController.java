package com.akebabi.backend.businesslogic.controllers;

import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.entities.SocialLink;
import com.akebabi.backend.businesslogic.exception.BadRequestException;
import com.akebabi.backend.businesslogic.services.BusinessAccountService;
import com.akebabi.backend.businesslogic.services.BusinessCategoryService;
import com.akebabi.backend.businesslogic.services.SocialLinksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    BusinessAccountService businessAccountService;
    @Autowired
    BusinessCategoryService businessCategoryService;
    @Autowired
    SocialLinksService socialLinksService;

    @PostMapping(value = "/secure/create-business-account/{categoryId}", consumes = {"application/json"})
    @Operation(description = "This API receive Business's Information and then Create New Business detail and return saved new business profile.")
    ResponseEntity<BusinessInfo> createBusiness(@Parameter(description = "Business's Information") @RequestBody BusinessInfo businessDetail,
                                                @PathVariable(required = true) Integer categoryId,
                                                @Parameter(description = "User's Public Id") @RequestHeader(value = "pid", required = true) String pid) throws Exception {
        BusinessInfo savedBusinessDetail = null;
        Map<String, String> errorsOnValidation = businessAccountService.isBusinessValid(businessDetail, pid);
        if (!errorsOnValidation.isEmpty()) {
            return new ResponseEntity(errorsOnValidation, HttpStatus.BAD_REQUEST);
        }

        savedBusinessDetail = businessAccountService.save(businessDetail, categoryId, pid);
        return ResponseEntity.created(new URI("/get-business-account/" + savedBusinessDetail.getBusinessPublicId()))
                .body(savedBusinessDetail);

    }


    @GetMapping(value = "/public/get-business-account/{businessPublicId}")
    @Operation(description = "This API receive Business's public ID and returns business information.")
    ResponseEntity<BusinessInfo> getBusiness(@Parameter(description = "Business's public ID ") @PathVariable(required = true) String businessPublicId) throws Exception {
        BusinessInfo savedBusinessDetail = null;

        savedBusinessDetail = businessAccountService.getBusinessByBusinessPublicId(businessPublicId);
        if (savedBusinessDetail == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(savedBusinessDetail, HttpStatus.FOUND);

    }

    @PostMapping(value = "/secure/update-business-profile")
    @Operation(description = "This API receive Business's Information and update business information.")
    ResponseEntity<BusinessInfo> updateBusiness(@Parameter(description = "Business's Information") @RequestBody BusinessInfo businessDetail,
                                                @Parameter(description = "User's Public Id") @RequestHeader(value = "pid", required = true) String pid) throws Exception {
        BusinessInfo savedBusinessDetail = null;
        if (businessDetail.getId() == null) {
            throw new BadRequestException("Business Id is required");
        }
        Map<String, String> errorsOnValidation = businessAccountService.isBusinessValid(businessDetail, pid);
        if (!errorsOnValidation.isEmpty()) {
            return new ResponseEntity(errorsOnValidation, HttpStatus.BAD_REQUEST);
        }
        savedBusinessDetail = businessAccountService.updateBusinessProfile(businessDetail);
        return new ResponseEntity(savedBusinessDetail, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/secure/delete-business-account/{businessPublicId}")
    @Operation(description = "This API receive Business's public ID and delete business information.")
    ResponseEntity<BusinessInfo> deleteBusiness(@Parameter(description = "Business's public ID ") @PathVariable(required = true) String businessPublicId) throws Exception {

        BusinessInfo savedBusinessDetail = null;
        BusinessInfo businessesByBusinessPublicId = businessAccountService.getBusinessByBusinessPublicId(businessPublicId);
        if (businessesByBusinessPublicId == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        boolean isDeleted = businessAccountService.delete(businessPublicId);
        if (!isDeleted) {
            throw new RuntimeException("Not able to delete");
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);

    }

    @GetMapping("/public/businessList")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "This API is for getting List of registered accounts")
    Page<BusinessInfo> getBusinesses(@PageableDefault() Pageable pageable) {
        return businessAccountService.getBusinessesList(pageable);
    }

    @GetMapping("/public/update-business-category/{businessPublicId}/{categoryId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(description = "This API is for updating business category")
    ResponseEntity<BusinessInfo> updateBusinessCategory(@PathVariable(required = true) String businessPublicId, @PathVariable(required = true) Integer categoryId) {
        BusinessInfo businessInfo = businessCategoryService.updateBusinessCategory(businessPublicId, categoryId);
        return new ResponseEntity(businessInfo, HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/secure/save-business-socialLink/{businessPublicId}")
    @Operation(description = "This API for saving social link.")
    ResponseEntity<Set<SocialLink>> saveBusinessSocialLinks(@Parameter(description = "Business's Social link Information")
                                                            @RequestBody SocialLink socialLink,
                                                            @PathVariable(required = true) String businessPublicId) throws Exception {
        Set<SocialLink> socialLinks = socialLinksService.save(socialLink, businessPublicId);

        return new ResponseEntity(socialLinks, HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/secure/update-business-socialLink/{businessPublicId}")
    @Operation(description = "This API for updating social link.")
    ResponseEntity<Set<SocialLink>> updateBusinessSocialLinks(@Parameter(description = "Business's Social link Information")
                                                            @RequestBody SocialLink socialLink,
                                                            @PathVariable(required = true) String businessPublicId) throws Exception {
        Set<SocialLink> socialLinks = socialLinksService.update(socialLink, businessPublicId);

        return new ResponseEntity(socialLinks, HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/secure/delete-business-socialLink/{businessPublicId}/{socialLinkId}")
    @Operation(description = "This API for updating social link.")
    ResponseEntity<Set<SocialLink>> updateBusinessSocialLinks(@Parameter(description = "Business's Social link Information")
                                                              @PathVariable(required = true) String businessPublicId,
                                                              @PathVariable(required = true) Integer socialLinkId) throws Exception {
        Set<SocialLink> socialLinks = socialLinksService.delete(socialLinkId,businessPublicId);

        return new ResponseEntity(socialLinks, HttpStatus.NO_CONTENT);
    }
}
