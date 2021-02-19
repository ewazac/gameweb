package com.example.demo.config;

import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class CreatedByAuditorConfig implements AuditorAware<String> {

    private final UserService userService;

    @Override
    public Optional<String> getCurrentAuditor() {
        String nick = null;
        try{
            nick = userService.getCurrentUser().getNick();
        } catch (EntityNotFoundException e) {

        }
        return Optional.ofNullable(nick);
    }
}
