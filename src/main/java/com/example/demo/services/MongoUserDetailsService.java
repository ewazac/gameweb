package com.example.demo.services;



import com.example.demo.model.AppUser;
import com.example.demo.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@Slf4j
public class MongoUserDetailsService implements UserDetailsService {


    @Autowired
    UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("wchodze do metody i szukam maila {}", email);
        AppUser appUser = repository.findUserByEmail(email);
        if(appUser == null) {
            log.info("nie udalo sie znalezc uzytkownika {}", email);
            throw new UsernameNotFoundException("User not found");
        }
        log.info("udalo sie znalezc uzytkownika {}", email);
        return User.withUsername(appUser.getEmail())
                .password(appUser.getPassword())
                .roles(appUser.getRoles().toArray(String[]::new)).build();
    }

    public AppUser findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }








}
