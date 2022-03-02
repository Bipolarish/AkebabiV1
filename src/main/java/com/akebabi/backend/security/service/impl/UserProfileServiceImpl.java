package com.akebabi.backend.security.service.impl;

import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.model.UserProfileModel;
import com.akebabi.backend.security.repo.UserRepo;
import com.akebabi.backend.security.service.UserProfileService;
import com.akebabi.backend.businesslogic.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserProfileModel getUserProfile(String userPublicId) {
        Optional<User> userByPublicId = userRepo.findByUserPublicId(userPublicId);
        if(userByPublicId.isPresent()){
            User user = userByPublicId.get();
            return new UserProfileModel().fromUserEntity(user);
        }
        return null;
    }

    @Override
    public UserProfileModel updateUserProfile(String pid, UserProfileModel userProfileModel) {
        Optional<User> userByPublicId = userRepo.findByUserPublicId(pid);
        if(userByPublicId.isPresent()){
            User exisitingUser=userByPublicId.get();
            exisitingUser.setUserPublicId(pid);
            exisitingUser.setUserName(userProfileModel.getEmail());
            exisitingUser.setFirstName(userProfileModel.getFirstName());
            exisitingUser.setLastName(userProfileModel.getLastName());
            exisitingUser.setPhoneNumber(userProfileModel.getPhoneNumber());
            User updatedUser = userRepo.save(exisitingUser);
            return new UserProfileModel().fromUserEntity(updatedUser);
        }
        throw new BadRequestException("Profile not found");
    }
}
