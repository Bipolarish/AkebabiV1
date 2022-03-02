package com.akebabi.backend.security.controller;

import com.akebabi.backend.security.entity.ConfirmationToken;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.model.AuthenticationRequest;
import com.akebabi.backend.security.model.CustomUserDetails;
import com.akebabi.backend.security.model.NewUserDetail;
import com.akebabi.backend.security.model.TokenPayLoad;
import com.akebabi.backend.security.repo.ConfirmationTokenRepository;
import com.akebabi.backend.security.repo.UserRepo;
import com.akebabi.backend.security.service.UserRegistrationService;
import com.akebabi.backend.security.utils.JwtTokenUtil;
import com.akebabi.backend.security.exception.UNAuthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Authentication Controller", description = "This Service Used for user registration and authentication.")
@RestController
@RequestMapping("/public")
public class AuthenticationController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserRegistrationService userRegistrationService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    @Operation(description = "This API receive user's User Name and  Password and return the user's profile.")
    public TokenPayLoad authenticate(@Parameter(description = "User's Public Id") @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUserName(),
                            authenticationRequest.getPassword()));
        } catch (Exception e) {
            throw new UNAuthorizedException("Username or/and password is not correct.");
        }
        User userByUserName = userRepo.findByUserName(authenticationRequest.getUserName());
        CustomUserDetails customUserDetails = new CustomUserDetails(userByUserName);
        String generatedToken = jwtTokenUtil.generateToken(customUserDetails);
        return new TokenPayLoad(generatedToken, userByUserName);

    }

    @PostMapping(value = "/createUser")
    @Operation(description = "This API receive User's Information and then Create New USer and return the user's profile.")
    ResponseEntity<NewUserDetail> createUser(@Parameter(description = "User's Information") @RequestBody NewUserDetail userDetail) throws Exception {
        System.out.println("it is coming to save the user");
        NewUserDetail savedUserDetail = null;
        if (!userRegistrationService.isUserNameExists(userDetail.getEmail())) {

            savedUserDetail = userRegistrationService.saveUser(userDetail);
            return ResponseEntity.created(new URI("/users/createUser"))
                    .body(savedUserDetail);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/confirmAccount")
    @Operation(description = "This API receive User's confirmation token as Parameter and activate user's account.")
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) throws Exception {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userRepo.findByUserName(token.getUser().getUserName());
            user.setEnabled(true);
            userRegistrationService.updateStatus(user);
            System.out.println("The account is activated");
            return new ResponseEntity("updated", HttpStatus.OK);
        }
        System.out.println("The link is invalid or broken!");
        return new ResponseEntity("not updated", HttpStatus.NOT_FOUND);
    }

}
