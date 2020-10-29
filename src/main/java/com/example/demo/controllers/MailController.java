package com.example.demo.controllers;

import com.example.demo.model.AppUser;

import com.example.demo.model.UserRepository;
import com.example.demo.services.MailService;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@CrossOrigin(origins = {"https://gameweb2.herokuapp.com/","https://localhost:4200"}, allowCredentials = "true")
public class MailController {

    private final MailService mailService;
    private final UserRepository userRepository;

    @Autowired
    public MailController(MailService mailService, UserRepository userRepository) {
        this.mailService = mailService;
        this.userRepository = userRepository;
    }


    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoOperations mongoOperations;

    @GetMapping(value = "/getAllCategories")
    public List<String> getAllCategories() {
        Aggregation aggregation = newAggregation(group("category"), project("category"));
        List<String> single = mongoOperations.aggregate(aggregation, "games", BasicDBObject.class).getMappedResults().stream().map(item -> item.getString("_id")).collect(Collectors.toList());
        return single;
    }

    @GetMapping(value = "/newsletter")
    public String sendMail(@RequestBody List<String> categories) throws MailException {
        try {
            if(categories != null) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                AppUser user = userRepository.findUserByEmail(userDetails.getUsername());
                user.setCategories(categories);
                userRepository.save(user);
                mailService.sendEmail();

            }
        } catch (MailException mailException) {
            System.out.println(mailException);
        }

        return "Zapisano do newslettera.";
    }


}
