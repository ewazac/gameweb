package com.example.demo.services;


import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Template;
import com.example.demo.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
/**Class used for sending e-mails for users*/
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

}
