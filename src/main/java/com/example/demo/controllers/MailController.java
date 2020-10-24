package com.example.demo.controllers;

import com.example.demo.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping(value = "/sendMail")
    public String sendMail() throws MailException {
        try {
            mailService.sendEmail();
        } catch (MailException mailException) {
            System.out.println(mailException);
        }

        return "Mail sent to user.";

    }

}
