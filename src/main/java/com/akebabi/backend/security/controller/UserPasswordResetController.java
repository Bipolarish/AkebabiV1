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
                PasswordResetToken passwordResetToken = passwordTokenRepo.findByUserId(user.getId());
                if(passwordResetToken!=null){
                    passwordTokenRepo.delete(passwordResetToken);
                    passwordResetService.updateResetPasswordToken(user.getUserName());
                }
                else{
                    passwordResetService.updateResetPasswordToken(user.getUserName());
                }
                 return new ResponseEntity ("We have sent a reset password link to your email ", HttpStatus.OK);

    }
    @PostMapping(value = "/checkPasswordToken")
    @Operation(description = "This API receive reset password  token as Parameter and update password.")
    public ResponseEntity<String> checkPasswordToken(@RequestParam("token") String token ) throws Exception {
        PasswordResetToken userToken= passwordResetService.getByResetPasswordToken(token);
        if(userToken!=null) {
            if(passwordResetService.isTokenExpired(userToken.getCreatedDate())){

                passwordTokenRepo.delete(userToken);
                return new ResponseEntity (" Token is Expired ", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity ("correct token", HttpStatus.OK);
        }
        else{
            return new ResponseEntity ("not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/reset-password")
    @Operation(description = "This API receive reset password  token as Parameter and update password.")
    public ResponseEntity<String> processResetPassword(@RequestParam("token") String token , @RequestBody User newPassword) throws Exception {

        PasswordResetToken userToken = passwordResetService.getByResetPasswordToken(token);
        if(userToken.getUser()!=null) {
            User user = userRepo.findById(userToken.getUser().getId()).get();
            passwordResetService.resetPassword(user, newPassword.getPassWord());
            return new ResponseEntity("Reset Successfully ", HttpStatus.OK);
            } else {
                return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
            }
        }
    }
