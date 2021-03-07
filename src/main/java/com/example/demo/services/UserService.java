package com.example.demo.services;

import com.example.demo.controllers.InvalidOldPasswordException;
import com.example.demo.controllers.UserFoundException;
import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;


    public void restartPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setActivateCode(UUID.randomUUID().toString());
        userRepository.save(user);
        Executors.newCachedThreadPool().execute(() -> {
            Context context = new Context();
            context.setVariable("activated_code", user.getActivateCode());
            mailService.sendMail("restartPassword", user.getEmail(), context);
        });
    }

    public User updateUser(User user) {
        String currentUser = getCurrentUser().getId();
        User userDb = getById(currentUser);
//        List<Review> reviews = reviewRepository.findByUserId(currentUser);
//        for (Review review : reviews) {
//            review.setNick(user.getNick());
//        }
//        reviewRepository.saveAll(reviews);
        userDb.setNick(user.getNick());
        userDb.setLastName(user.getLastName());
        userDb.setFirstName(user.getFirstName());
        userDb.setNewsletter(!userDb.isNewsletter());
//        if(userDb.isNewsletter()) {
//            Executors.newCachedThreadPool().execute(() -> {
//                Context context = new Context();
//                mailService.sendMail("potwierdzenie", user.getEmail(), context);
//            });
//        }
        userDb.setPoint(user.getPoint());
        return userRepository.save(userDb);
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(User user) throws UserFoundException {
        String email = user.getEmail();
        Optional<User> checkUser = userRepository.findByEmail(email);
        if(checkUser.isPresent()) {
            throw new UserFoundException();
        }
        return userRepository.save(user);
    }

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not logged"));
    }

    public User uploadAvatar(MultipartFile multipartFile) throws IOException {
        User user = getCurrentUser();
        user.setAvatar(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        return userRepository.save(user);
    }

    public void changePassword(String newPassword, String oldPassword) throws InvalidOldPasswordException {
        User user = getCurrentUser();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void changeNewsletter() {
        User user = getCurrentUser();
        user.setNewsletter(!user.isNewsletter());
        userRepository.save(user);
    }

    public void changeRestartPassword(String activatedCode, String password) throws Throwable {
        User user = userRepository.findByActivateCode(activatedCode).orElseThrow(() -> new EntityNotFoundException("Cannot find user by activated code"));
        user.setActivateCode(null);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void addPoint() {
        User currentUser = getCurrentUser();
        currentUser.setPoint(currentUser.getPoint() + 1);
        userRepository.save(currentUser);
    }

}
