package com.akebabi.backend.businesslogic.controllers;

import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.services.BusinessCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    BusinessCategoryService businessCategoryService;


    @GetMapping("/public/list")
    @Operation(description = "This API gives list of categories")
    ResponseEntity<BusinessInfo> getCategoryList() {
        return new ResponseEntity(businessCategoryService.getCategories(), HttpStatus.OK);
    }
}
