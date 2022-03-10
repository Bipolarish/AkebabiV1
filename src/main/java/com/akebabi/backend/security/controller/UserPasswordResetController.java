package com.akebabi.backend.security.controller;


import com.akebabi.backend.security.entity.PasswordResetToken;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.repo.PasswordTokenRepo;
import com.akebabi.backend.security.repo.UserRepo;
import com.akebabi.backend.security.service.EmailService;
import com.akebabi.backend.security.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class UserPasswordResetController {

    @Autowired
    PasswordResetService passwordResetService;
    @Autowired
    PasswordTokenRepo passwordTokenRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/forget_password")
    public ResponseEntity resetUserPassword(@RequestBody User user)throws Exception {
        passwordResetService.updateResetPasswordToken(user.getUserName());
        return new ResponseEntity ("We have sent a reset password code to your email Please check ", HttpStatus.OK);

    }
    @PostMapping(value = "/checkPasswordToken")
    @Operation(description = "This API receive reset password  token as Parameter and update password.")
    public ResponseEntity<String> checkPasswordToken(@RequestParam("token") String token)  throws Exception {
        PasswordResetToken userToken= passwordResetService.getByResetPasswordToken(token);
        if(userToken!=null) {
            if(passwordResetService.isTokenExpired(userToken.getCreatedDate())){
                return new ResponseEntity (" Token is Expired ", HttpStatus.BAD_REQUEST);
            }
            if(!passwordResetService.isValidToken(token)){
                return new ResponseEntity ("Incorrect token", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity ("Correct Token", HttpStatus.OK);
        }
            return new ResponseEntity ("not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/reset-password")
    @Operation(description = "This API receive reset password  token as Parameter and update password.")
    public ResponseEntity<String> processResetPassword(@RequestParam("token") String token , @RequestBody User newPassword) throws Exception {

        PasswordResetToken userToken = passwordResetService.getByResetPasswordToken(token);
        if(userToken!=null && userToken.getUser()!=null) {
            User user = userRepo.findById(userToken.getUser().getId()).get();
            passwordResetService.resetPassword(user, newPassword.getPassWord());
            return new ResponseEntity("Reset Successfully ", HttpStatus.OK);
        }
        return new ResponseEntity("Token not found", HttpStatus.NOT_FOUND);

        }
    }
