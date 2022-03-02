package com.akebabi.backend.security.service.impl;

import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public String sendEmail(User savedUser) throws Exception {
        Context context = new Context();
        context.setVariable("user", savedUser);
        String process = templateEngine.process("emails/welcome", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Welcome " + savedUser.getFirstName());
        helper.setText(process, true);
        helper.setTo(savedUser.getUserName());
        helper.setFrom("no-reply@betdelala.com");
        javaMailSender.send(mimeMessage);

        return "Sent";
    }

    @Override
    public String sendEmailForPasswordReset(User user) throws Exception {

        Context context = new Context();
        context.setVariable("user", user);
        String process = templateEngine.process("emails/resetPassword",context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject("Here's the link to reset your password");
        mimeMessageHelper.setText(process, true);
        mimeMessageHelper.setTo((user.getUserName()));

        javaMailSender.send(mimeMessage);
        return "sent";
    }
}
