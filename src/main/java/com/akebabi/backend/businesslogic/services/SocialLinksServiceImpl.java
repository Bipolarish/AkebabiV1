package com.akebabi.backend.businesslogic.services;

import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.entities.SocialLink;
import com.akebabi.backend.businesslogic.exception.BadRequestException;
import com.akebabi.backend.businesslogic.repos.BusinessAccountRepo;
import com.akebabi.backend.businesslogic.repos.SocialLinksRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class SocialLinksServiceImpl implements SocialLinksService{

    private final SocialLinksRepo socialLinksRepo;
    private final BusinessAccountService businessAccountService;
    private final BusinessAccountRepo businessAccountRepo;

    @Override
    public Set<SocialLink> save(SocialLink socialLink, String businessPublicId) {
        BusinessInfo existingBusinessInfo = businessAccountService.getBusinessByBusinessPublicId(businessPublicId);
        existingBusinessInfo.getSocialLinks().add(socialLink);
        BusinessInfo savedBusiness = businessAccountRepo.save(existingBusinessInfo);
        return savedBusiness.getSocialLinks();
    }

    @Override
    public Set<SocialLink> update(SocialLink socialLink, String businessPublicId) {
        if(socialLink.getId()==null){
            throw new BadRequestException("Social link id is required");
        }
        socialLinksRepo.save(socialLink);
        BusinessInfo businessesInfo = businessAccountService.getBusinessByBusinessPublicId(businessPublicId);
        return businessesInfo.getSocialLinks();
    }

    @Override
    public Set<SocialLink> delete(Integer socialLinkId, String businessPublicId) {
        SocialLink socialLinkToDelete = socialLinksRepo.findById(socialLinkId).orElseThrow(() -> new BadRequestException("Couldnt find social link with specified ID"));
        socialLinksRepo.delete(socialLinkToDelete);
        BusinessInfo businessesInfo = businessAccountService.getBusinessByBusinessPublicId(businessPublicId);
        return businessesInfo.getSocialLinks();
    }
}
