package com.akebabi.backend.businesslogic.utils;

import com.akebabi.backend.businesslogic.entities.*;
import com.akebabi.backend.businesslogic.entities.BusinessInfo;
import com.akebabi.backend.businesslogic.services.BusinessAccountService;
import com.akebabi.backend.businesslogic.services.BusinessCategoryService;
import com.akebabi.backend.businesslogic.services.SocialLinksService;
import com.akebabi.backend.security.entity.Role;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.enums.DAY_OF_WEEK;
import com.akebabi.backend.security.enums.RoleEnum;
import com.akebabi.backend.security.enums.SOCIAL_LINK_TYPE;
import com.akebabi.backend.security.model.NewUserDetail;
import com.akebabi.backend.security.repo.UserRepo;
import com.akebabi.backend.security.service.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final BusinessCategoryService businessCategoryService;
    private final BusinessAccountService businessAccountService;
    private final SocialLinksService socialLinksService;
    private final UserRepo userRepo;
    private final UserRegistrationService userRegistrationService;

    @PostConstruct
    public void initalize() {
//        System.out.println("Initialize...........");
//        businessCategoryService.save(new BusinessCategory(null, "Financial",null));
//        businessCategoryService.save(new BusinessCategory(null, "Health",null));
//        businessCategoryService.save(new BusinessCategory(null, "Commerce", null));

//        Role adminRole = new Role(RoleEnum.ADMIN);
//        Set<Role> adminRoleset = new HashSet<>();
//        adminRoleset.add(adminRole);
//        String adminUUID = UUID.randomUUID().toString();
//
//        String userUUID = UUID.randomUUID().toString();
//        Role userRole = new Role(RoleEnum.USER);
//        Set<Role> userRoleset = new HashSet<>();
//        userRoleset.add(userRole);
//        NewUserDetail newUserDetail = new NewUserDetail(userUUID, "Alemayew", "testma", "4699871814", "123456", "user@gmail.com");
//        NewUserDetail newAdminDetail = new NewUserDetail(userUUID, "Miftah", "Kemal ", "4699871815", "adminadmin", "admin@gmail.com");
//
//        NewUserDetail userDetail = userRegistrationService.saveUser(newUserDetail, userRoleset);
//        NewUserDetail adminDetail1 = userRegistrationService.saveUser(newAdminDetail, adminRoleset);
//        String publicId= UUID.randomUUID().toString();
//
//        Set<SocialLink> socialLinks = new HashSet<>();
//        socialLinks.add(new SocialLink(null,"http://fb/abcf",0, SOCIAL_LINK_TYPE.FACE_BOOK));
//        socialLinks.add(new SocialLink(null,"http://youtube/abcf",0, SOCIAL_LINK_TYPE.YOUTUBE));
//        Set<OpenHours> openHours = new HashSet<>();
//        openHours.add(new OpenHours(null, 0,DAY_OF_WEEK.MONDAY,"8:00 AM", "5:00 PM"));
//        openHours.add(new OpenHours(null, 0,DAY_OF_WEEK.WEDNESDAY,"8:00 AM", "5:00 PM"));
//        Set<BusinessImage> businessImages = new HashSet<>();
//        businessImages.add(new BusinessImage(null, "https://xyz/teest.jpg",true));
//        businessImages.add(new BusinessImage(null, "https://xyz/teest2.jpg",false));
//        Set<Contacts> contacts = new HashSet<>();
//        contacts.add(new Contacts(null, true, "kebede","alemu","0911-234567","user@gmail.com","8:00AM - 4:00PM","https://imageServer/profileimage/xyz.jpg"));
//        contacts.add(new Contacts(null, false, "kemal","ahmed","0911-8764567","user2@gmail.com","8:00AM - 4:00PM","https://imageServer/profileimage/xyz2.jpg"));
//        contacts.add(new Contacts(null, false, "teshome","brhanu","0911-999567","user3@gmail.com","8:00AM - 4:00PM","https://imageServer/profileimage/xyz3.jpg"));
//        BusinessInfo financialBusiness =
//                new BusinessInfo(null,publicId,"AbcFinancialx","abc_Financialx","Abc financial description", "abcx@gmail.com", "www.abcf.com","+251911701770","banking, finance, money","piyasa","addis abab","19999","37.342","9.07",null,socialLinks,openHours,businessImages,contacts,null);
//        User existingUser = userRepo.findById(1).get();
//
//        boolean businessValid = businessAccountService.isBusinessValid(financialBusiness);
//        System.out.println("is Business valid:"+ businessValid);
//        businessAccountService.save(financialBusiness,1, existingUser.getUserPublicId());
//        List<BusinessInfo> businessesByCategory = businessCategoryService.getBusinessesByCategory(1);
//        for(BusinessInfo business: businessesByCategory) {
//            System.out.println("Business name:"+business.getName()+" social links"+ business.getSocialLinks().stream().map(link->link.getEnumSocialLinkType().name()).collect(Collectors.joining(",")));
//        }

//        Page<BusinessInfo> businessesList = businessAccountService.getBusinessesList(PageRequest.of(1, 2));
//        BusinessInfo businessInfo = businessesList.get().findFirst().get();




    }
}
