package com.akebabi.backend.security.service.impl;

import com.akebabi.backend.security.entity.PasswordResetToken;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.model.PasswordResetTokenModel;
import com.akebabi.backend.security.model.UserPasswordRestModel;
import com.akebabi.backend.security.repo.PasswordTokenRepo;
import com.akebabi.backend.security.repo.UserRepo;
import com.akebabi.backend.security.service.EmailService;
import com.akebabi.backend.security.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import java.util.UUID;


@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordTokenRepo passwordTokenRepo;

    @Autowired
    EmailService emailService;
    @Autowired
    PasswordResetService passwordResetService;



    private static final long EXPIRATION = 30;


    @Override
    public void updateResetPasswordToken(String email) throws Exception {
        User user = userRepo.findByUserName(email);


        if (user != null) {
            PasswordResetToken passwordResetToken = passwordTokenRepo.findByUserId(user.getId());

             if( passwordResetToken == null){
                 passwordResetToken = new PasswordResetToken(user);
             }
             String token = UUID.randomUUID().toString().substring(0, 6) + "-" + email;
             passwordResetToken.setToken(token);


            passwordTokenRepo.save(passwordResetToken);
            emailService.sendEmailForPasswordReset(token);

        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    public PasswordResetToken getByResetPasswordToken(String token) {

        return passwordTokenRepo.findByToken(token);
    }

    @Override
    public void resetPassword(User user, String newPassword) {
        user.setPassWord(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        User user1 = userRepo.findByUserName(user.getUserName());
        PasswordResetToken passwordResetTokenModel = passwordTokenRepo.findByUserId(user1.getId());
        if (passwordResetTokenModel != null) {
            passwordTokenRepo.delete(passwordResetTokenModel);
            userRepo.save(user);
        }

    }

    @Override
    public boolean isTokenExpired(LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);
        return diff.toMinutes() >= EXPIRATION;
    }

    @Override
    public boolean isValidToken(String token) {

        PasswordResetToken userToken = passwordResetService.getByResetPasswordToken(token);
        return userToken!=null;
    }
}