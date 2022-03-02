package com.akebabi.backend.businesslogic.services;

import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BusinessAccountService {

    Map<String, String> isBusinessValid(BusinessInfo businessDetail, String pid);

    BusinessInfo save(BusinessInfo businessDetail, Integer categoryId, String posterPublicId);
    BusinessInfo updateBusinessProfile(BusinessInfo businessDetail);
    boolean delete(String businessPublicId);
    Page<BusinessInfo> getBusinessesList(Pageable pageable);
    Page<BusinessInfo> getBusinessesList(String filterKey, Pageable pageable);
    Page<BusinessInfo> getBusinessesList(Integer categoryId, Pageable pageable);
    List<BusinessInfo> userBusinessesList(String userPublicId);
    BusinessInfo getBusinessByBusinessPublicId(String userPublicId);


}
