package com.akebabi.backend.security.service.impl;

import com.akebabi.backend.security.entity.ConfirmationToken;
import com.akebabi.backend.security.entity.Role;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.model.NewUserDetail;
import com.akebabi.backend.security.repo.ConfirmationTokenRepository;
import com.akebabi.backend.security.repo.RoleRepo;
import com.akebabi.backend.security.repo.UserRepo;
import com.akebabi.backend.security.service.EmailService;
import com.akebabi.backend.security.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public NewUserDetail saveUser(NewUserDetail newUserDetail) throws Exception {
        User user = new User();
        user.setUserName(newUserDetail.getEmail());
        user.setPassWord(newUserDetail.getUserPassword());
        user.setCreatedOn(new Date());
        user.setEnabled(false);
        user.setLocked(false);
        user.setRoles(roleRepo.findAllByname("USER"));

        user.setPhoneNumber(newUserDetail.getPhoneNumber());
        user.setFirstName(newUserDetail.getFirstName());
        user.setLastName(newUserDetail.getLastName());
        user.setUserPublicId(UUID.randomUUID().toString());


        user.setPassWord(BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt()));
        User savedUser = userRepo.save(user);
        newUserDetail.setUserPublicId(savedUser.getUserPublicId());

        ConfirmationToken confirmationToken = new ConfirmationToken(savedUser);

        confirmationTokenRepository.save(confirmationToken);

        savedUser.setActivationUrl("http://localhost:8080/public/confirmAccount?token=" + confirmationToken.getConfirmationToken());

        emailService.sendEmail(savedUser);

        return newUserDetail;


    }

    @Override
    public User saveUser(User user) {
        user.setPassWord(BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt()));
        User savedUser = userRepo.save(user);
        return savedUser;
    }

    @Override
    public User updateStatus(User user) {
        return userRepo.save(user);
    }

    @Override
    public NewUserDetail saveUser(NewUserDetail newUserDetail, Set<Role> roles) {
        User user = new User();
        user.setUserName(newUserDetail.getEmail());
        user.setPassWord(newUserDetail.getUserPassword());
        user.setFirstName(newUserDetail.getFirstName());
        user.setLastName(newUserDetail.getLastName());
        user.setCreatedOn(new Date());
        user.setEnabled(false);
        user.setLocked(false);

        user.setRoles(roles);

        user.setPhoneNumber(newUserDetail.getPhoneNumber());

        String publicId = UUID.randomUUID().toString();
        user.setUserPublicId(publicId);
        System.out.println(publicId);

        for(Role role:roles) {
            roleRepo.save(role);
        }

        user.setPassWord(BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt()));
        User savedUser = userRepo.save(user);
        newUserDetail.setUserPublicId(savedUser.getUserPublicId());
        return newUserDetail;
    }

    public boolean isUserNameExists(String userName) {
        User userInfo = userRepo.findByUserName(userName);
        if (userInfo != null)
            return true;
        return false;

    }

    @Override
    public User getUser(String userPublicId) {
        Optional<User> userByUserPublicId = userRepo.findByUserPublicId(userPublicId);
        if (userByUserPublicId.isPresent()) {
            return userByUserPublicId.get();
        }
        return null;
    }

}
