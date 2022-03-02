package com.akebabi.backend.security.service;

import com.akebabi.backend.security.entity.PasswordResetToken;
import com.akebabi.backend.security.entity.User;

import java.time.LocalDateTime;


public interface PasswordResetService {

   void updateResetPasswordToken( String email) throws Exception;
   PasswordResetToken getByResetPasswordToken(String token);
   void resetPassword(User user, String newPassword);
   boolean isTokenExpired(final LocalDateTime tokenCreationDate);

}
