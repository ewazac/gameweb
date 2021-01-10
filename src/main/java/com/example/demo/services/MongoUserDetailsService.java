package com.example.demo.services;



import com.example.demo.model.dao.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MongoUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository repository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("wchodze do metody i szukam maila {}", email);
        User user = repository.findByEmail(email).orElseThrow();
        if(user == null) {
            log.info("nie udalo sie znalezc uzytkownika {}", email);
            throw new UsernameNotFoundException("User not found");
        }
        log.info("udalo sie znalezc uzytkownika {}", email);
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(String[]::new)).build();
    }

    public User findUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }








}
