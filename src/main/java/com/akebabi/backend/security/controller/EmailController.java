package com.akebabi.backend.security.controller;

import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    @ResponseBody
    public String sendMail(@RequestBody User user) throws Exception {
        emailService.sendEmail(user);
        return "Email Sent Successfully.!";
    }

}
