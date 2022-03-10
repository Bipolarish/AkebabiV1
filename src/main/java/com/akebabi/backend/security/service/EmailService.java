package com.akebabi.backend.security.service;

import com.akebabi.backend.security.entity.User;

public interface EmailService {

    String sendEmail(User savedUser) throws Exception;
    String sendEmailForPasswordReset(String userPasswordRestModel) throws Exception;
    
}
