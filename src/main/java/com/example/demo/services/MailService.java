package com.example.demo.services;


import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Template;
import com.example.demo.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final TemplateRepository templateRepository;


    public void sendMail(String templateName, String receiver, Context context) {
        Template template = templateRepository.findByName(templateName).orElseThrow(() -> new EntityNotFoundException("Template not found."));
        String body = templateEngine.process(template.getBody(), context);
        javaMailSender.send(message -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setFrom("gameweb@gmail.com");
            mimeMessageHelper.setSubject(template.getSubject());
            mimeMessageHelper.setText(body, true);
        });
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
