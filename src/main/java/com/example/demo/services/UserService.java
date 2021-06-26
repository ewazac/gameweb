package com.example.demo.services;

import com.example.demo.controllers.InvalidOldPasswordException;
import com.example.demo.controllers.UserFoundException;
import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Favourites;
import com.example.demo.model.dao.News;
import com.example.demo.model.dao.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
//@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final FileService fileService;
    private final FavoritesService favoritesService;
    private final NewsService newsService;


    /**Sends e-mail with link to restart password*/
    public void restartPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setActivateCode(UUID.randomUUID().toString());
        userRepository.save(user);
        Executors.newCachedThreadPool().execute(() -> {
            Context context = new Context();
            context.setVariable("activated_code", user.getActivateCode());
            mailService.sendMail("restartPassword2", user.getEmail(), context);
        });
    }

    /**Updates user*/
    public User updateUser(User user) {
        String currentUser = getCurrentUser().getId();
        User userDb = getById(currentUser);
        userDb.setNick(user.getNick());
        userDb.setLastName(user.getLastName());
        userDb.setFirstName(user.getFirstName());
        userDb.setNewsletter(!userDb.isNewsletter());
        userDb.setPoint(user.getPoint());
        return userRepository.save(userDb);
    }

    /**Returns user by id*/
    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    /**Returns all users*/
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**Saves user - registration*/
    public User save(User user) throws UserFoundException {
        String email = user.getEmail();
        Optional<User> checkUser = userRepository.findByEmail(email);
        if(checkUser.isPresent()) {
            throw new UserFoundException();
        }
        Executors.newCachedThreadPool().execute(() -> {
            Context context = new Context();
            mailService.sendMail("rejestracja", user.getEmail(), context);
        });
        return userRepository.save(user);
    }

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    /**Returns user's profile with particular data*/
    public UserDto getUserDetails(String id) {
        User user = getById(id);
        List<Favourites> favouritesForUser = favoritesService.getFavouritesForUser(user.getId());
        Map<Boolean, List<String>> ids = favouritesForUser.stream().collect(Collectors.partitioningBy(favourites -> favourites.getGameId() != null, Collectors.mapping(favourites -> favourites.getGameId() != null ? favourites.getGameId() : favourites.getNewsId(), Collectors.toList())));
        List<String> gamesIds = ids.get(true);
        List<String> newsIds = ids.get(false);
        List<News> news = newsService.getNews(newsIds);
        return UserDto.builder()
                .gamesIds(gamesIds)
                .news(news)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .point(user.getPoint())
                .nick(user.getNick())
                .build();
    }

    /**Get user - log in*/
    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not logged"));
    }

    /**Returns list of users - used in favourites*/
    public List<User> getUsers(List<String> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    /**Uploads avatar*/
    public User uploadAvatar(MultipartFile multipartFile) throws IOException {
        User user = getCurrentUser();
        user.setImageUrl(user.getId() + ".png");
        fileService.uploadFile(multipartFile.getBytes(), user.getId() + ".png");
        return userRepository.save(user);
    }

    /**Changes password in user's profile*/
    public void changePassword(String newPassword, String oldPassword) throws InvalidOldPasswordException {
        User user = getCurrentUser();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException();
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**Subscribe / unsubscribe to newsletter*/
    public void changeNewsletter() {
        User user = getCurrentUser();
        user.setNewsletter(!user.isNewsletter());
        if(user.isNewsletter()) {
            Executors.newCachedThreadPool().execute(() -> {
                Context context = new Context();
                mailService.sendMail("potwierdzenieNewsletter", user.getEmail(), context);
            });
        }
        userRepository.save(user);
    }

    /**Change user's password with activated code from sent e-mail*/
    public void changeRestartPassword(String activatedCode, String password) throws Throwable {
        User user = userRepository.findByActivateCode(activatedCode).orElseThrow(() -> new EntityNotFoundException("Cannot find user by activated code"));
        user.setActivateCode(null);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    /**Adds point to user's profile after adding review*/
    public void addPoint() {
        User currentUser = getCurrentUser();
        currentUser.setPoint(currentUser.getPoint() + 1);
        userRepository.save(currentUser);
    }

}
