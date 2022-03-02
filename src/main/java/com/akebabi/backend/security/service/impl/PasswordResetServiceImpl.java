package com.akebabi.backend.security.service.impl;

import com.akebabi.backend.security.entity.PasswordResetToken;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.repo.PasswordTokenRepo;
import com.akebabi.backend.security.repo.UserRepo;
import com.akebabi.backend.security.service.EmailService;
import com.akebabi.backend.security.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
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


    private static final long EXPIRATION = 30;


    @Override
    public void updateResetPasswordToken( String email) throws Exception {
        User user = userRepo.findByUserName(email);
        if(user !=null){
            PasswordResetToken passwordResetToken = passwordTokenRepo.findByUserId(user.getId());

            if(passwordResetToken!=null){
                passwordResetToken.setToken(UUID.randomUUID().toString());
            }else{
                passwordResetToken = new PasswordResetToken(user);

            }
            passwordTokenRepo.save(passwordResetToken);

            user.setActivationUrl("http://localhost:8080/public/checkPasswordToken?token=" +passwordResetToken.getToken());
            userRepo.save(user);
           emailService.sendEmailForPasswordReset(user);


        }else {
            throw new UsernameNotFoundException("Could not find any customer with the email " +email);
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
        PasswordResetToken passwordResetToken = passwordTokenRepo.findByUserId(user1.getId());
        if(passwordResetToken !=null){
            passwordTokenRepo.delete(passwordResetToken);
            userRepo.save(user);
        }

    }
    @Override
    public boolean isTokenExpired(LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRATION;
    }
}
