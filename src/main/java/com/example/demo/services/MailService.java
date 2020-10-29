package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendEmail(List<String> categories) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mimeMessageHelper.setTo(userDetails.getUsername());
        mimeMessageHelper.setSubject("Potwierdzenie zapisania do newslettera");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Zapisałeś się do newslettera w kategoriach:").append(System.lineSeparator());
        for(String value : categories) {
            stringBuilder.append(value).append(" ");
        }

        mimeMessageHelper.addAttachment("logo.png", new ClassPathResource("logo.png"));
        String inlineImage = "<img src=\"cid:logo.png\"></img><br/>";
        mimeMessageHelper.setText(stringBuilder.toString() + inlineImage, true);


        javaMailSender.send(mimeMessage);
    }
}
