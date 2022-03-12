package com.akebabi.backend.security.service;

import com.akebabi.backend.security.entity.PasswordResetToken;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.model.PasswordResetTokenModel;
import com.akebabi.backend.security.model.UserPasswordRestModel;

import java.time.LocalDateTime;


public interface PasswordResetService {

   String updateResetPasswordToken(String email) throws Exception;
   PasswordResetToken getByResetPasswordToken(String token);
   void resetPassword(User user, String newPassword);
   boolean isTokenExpired(final LocalDateTime tokenCreationDate);
   boolean isValidToken(String token);

}
