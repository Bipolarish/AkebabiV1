package com.akebabi.backend.businesslogic.repos;

import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.entities.BusinessCategory;
import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.entities.SocialLink;
import com.akebabi.backend.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessAccountRepo extends JpaRepository<BusinessInfo, Integer> {

    List<BusinessInfo> findAllByProfileUrlNameIgnoreCase(String profileName);
    List<BusinessInfo> findAllByEmailIgnoreCase(String email);
    List<BusinessInfo> findAllByPrimaryPhoneIgnoreCase(String primaryPhone);
    List<BusinessInfo> findAllByCategory(BusinessCategory category);
    Page<BusinessInfo> findAllByCategory(BusinessCategory category, Pageable pageable);
    Page<BusinessInfo> findAllByNameContainsIgnoreCase(String filterKey, Pageable pageable);
    List<BusinessInfo> findAllByBusinessPublicId(String businessPublicId);
    List<BusinessInfo> findAllByPostedBy(User poster);
}
