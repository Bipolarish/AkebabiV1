package com.akebabi.backend.businesslogic.services;

import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.entities.OpenHours;
import com.akebabi.backend.businesslogic.exception.BadRequestException;
import com.akebabi.backend.businesslogic.repos.BusinessAccountRepo;
import com.akebabi.backend.businesslogic.repos.OpenHoursRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public abstract class OpenHoursServiceImpl implements OpenHoursService{

    private final OpenHoursRepo openHoursRepo;
    private final BusinessAccountService businessAccountService;
    private final BusinessAccountRepo businessAccountRepo;

    @Override
    public Set<OpenHours> save(OpenHours openHours, String businessPublicID){
        BusinessInfo existingBusinessInfo = businessAccountService.getBusinessByBusinessPublicId(businessPublicID);
        existingBusinessInfo.getOpenHours().add(openHours);
        BusinessInfo savedBusiness = businessAccountRepo.save(existingBusinessInfo);
        return savedBusiness.getOpenHours();
    }

    @Override
    public Set<OpenHours> update(OpenHours openHours, String businessPublicId) {
        if(openHours.getId()==null){
            throw new BadRequestException("Open hours Id is required");
        }
        openHoursRepo.save(openHours);
        BusinessInfo businessInfo= businessAccountService.getBusinessByBusinessPublicId(businessPublicId);
        return businessInfo.getOpenHours();
    }


    @Override
    public Set<OpenHours> delete(Integer openHoursId, String businessPublicID) {
        OpenHours openHoursToDelete= openHoursRepo.findById(openHoursId).orElseThrow(() -> new BadRequestException("Couldn't find the Open Hour"));
        openHoursRepo.delete(openHoursToDelete);
        BusinessInfo businessInfo= businessAccountService.getBusinessByBusinessPublicId(businessPublicID);
        return businessInfo.getOpenHours();
    }

}
