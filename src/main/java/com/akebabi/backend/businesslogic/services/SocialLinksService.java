package com.akebabi.backend.businesslogic.services;

import com.akebabi.backend.businesslogic.entities.SocialLink;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface SocialLinksService {
    Set<SocialLink> save(SocialLink socialLink, String businessPublicId);
    Set<SocialLink> update(SocialLink socialLink, String businessPublicId);
    Set<SocialLink> delete(Integer socialLinkId, String businessPublicId);

}
