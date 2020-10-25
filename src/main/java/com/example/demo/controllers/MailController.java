package com.example.demo.controllers;

import com.example.demo.model.AppUser;
import com.example.demo.model.UserRepository;
import com.example.demo.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Boolean.TRUE;

@RestController
public class MailController {

    private final MailService mailService;
    private final UserRepository userRepository;

    @Autowired
    public MailController(MailService mailService, UserRepository userRepository) {
        this.mailService = mailService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/newsletter")
    public String sendMail() throws MailException {
        try {
            mailService.sendEmail();

        } catch (MailException mailException) {
            System.out.println(mailException);
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = userRepository.findUserByEmail(userDetails.getUsername());
        user.setNewsletter(TRUE);
        userRepository.save(user);
        return "Zapisano do newslettera.";

    }

}
