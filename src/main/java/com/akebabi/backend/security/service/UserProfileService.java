package com.akebabi.backend.security.service;

import com.akebabi.backend.security.model.UserProfileModel;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {

    UserProfileModel getUserProfile(String userPublicId);

    UserProfileModel updateUserProfile(String pid, UserProfileModel userProfileModel);

}
