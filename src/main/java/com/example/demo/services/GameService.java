package com.example.demo.services;

import com.example.demo.controllers.UserFoundException;
import com.example.demo.model.dao.Game;
import com.example.demo.model.dao.User;
import com.example.demo.repository.GamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GamesRepository gamesRepository;
    private final UserService userService;

    /**Method for voting for a game in competition*/
    public void vote(String gameId, String groupId) throws UserFoundException {
        Game game = gamesRepository.findByIdAndGroupId(gameId, groupId).orElseThrow();
        List<Game> gameListByGroupId = gamesRepository.findByGroupId(groupId);
        User currentUser = userService.getCurrentUser();
        for(Game g: gameListByGroupId) {
            if(g.getUsers() == null) {
                if(game.getUsers() == null) {
                    game.setUsers(new HashSet<>());
                }
            }
            else if(g.getUsers().contains(currentUser)) {
                throw new UserFoundException();
            }
        }

        game.getUsers().add(currentUser);
        game.setCounter(game.getCounter() + 1);
        gamesRepository.save(game);

    }

    /**Saves competition*/
    public List<Game> save(List<Game> games) {
        String groupId = UUID.randomUUID().toString();
        games.forEach(game -> game.setGroupId(groupId));
        games.forEach(game -> game.setCounter(0));
        return gamesRepository.saveAll(games);
    }

    /**Returns list of games in paricular competition*/
    public List<Game> findByGroupId(String groupId) {
        return gamesRepository.findByGroupId(groupId).stream()
                .peek(game -> {
                    if (game.getUsers() != null)
                        game.setCounter(game.getUsers().size());
                    else {
                        game.setCounter(0);
                    }
                })
                .collect(Collectors.toList());
    }

    /**Method for finding recommended game in particular competition*/
    public Game findRecommendedGame(String groupId) {
        Game game = gamesRepository.findByGroupId(groupId).stream().max(Comparator.comparing(Game::getCounter)).get();
        game.setRecommended(true);
        game.setRecommendedDate(LocalDate.now());
        return gamesRepository.save(game);
    }

    /**Returns all recommended games*/
    public List<Game> findRecommendedGamesList() {
        return gamesRepository.findByRecommendedIsTrue();
    }

    /**Returns all competitions*/
    public Map<String, List<Game>> findLists() {
        return gamesRepository.findAll().stream().collect(Collectors.groupingBy(game -> game.getGroupId()));
    }

    /**Deletes competition by id*/
    public void deleteGroupById(String groupId) {
        gamesRepository.deleteByGroupId(groupId);
    }
}


