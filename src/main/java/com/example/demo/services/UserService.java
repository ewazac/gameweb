package com.example.demo.services;

import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.AppUser;
import com.example.demo.model.dao.Forum;
import com.example.demo.repository.ForumRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<AppUser> getAll() {
        return userRepository.findAll();
    }

    public AppUser save(AppUser appUser) {
        return userRepository.save(appUser);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public AppUser getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public AppUser getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not logged"));
    }




}
