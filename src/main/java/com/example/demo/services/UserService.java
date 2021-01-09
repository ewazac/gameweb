package com.example.demo.services;

import com.example.demo.controllers.InvalidOldPasswordException;
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

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

//    public void deleteById(String id) {
//        userRepository.deleteById(id);
//    }

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

    public void changeLogin(String newLogin, String oldLogin) {
        User user = getCurrentUser();
        if(newLogin.equals(oldLogin)) {
            System.out.println("The same login");
        }
        user.setEmail(newLogin);
        userRepository.save(user);
    }





}
