package com.example.demo.services;


import com.example.demo.model.AppUser;
import com.example.demo.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
@Component
@Slf4j
public class MongoUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("wchodzę do metody i szukam maila {}", email);
        AppUser appUser = repository.findUserByEmail(email);
        if(appUser == null) {
            log.info("nie udało się znaleźć użytkownika {}", email);
            throw new UsernameNotFoundException("User not found");
        }
        log.info("udało się znaleźć użytkownika {}", email);
        return User.withUsername(appUser.getEmail())
                .password(appUser.getPassword())
                .roles(appUser.getRoles().toArray(String[]::new)).build();
    }
}
