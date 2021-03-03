package com.example.demo.services;

import com.example.demo.model.dao.Game;
import com.example.demo.model.dao.User;
import com.example.demo.repository.GamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GamesRepository gamesRepository;
    private final UserService userService;

    public void vote(String gameId) {
        Game game = gamesRepository.findById(gameId).orElseThrow();
        User currentUser = userService.getCurrentUser();
        if(game.getUsers() == null) {
            game.setUsers(new HashSet<>());
        }
        game.getUsers().add(currentUser);
        gamesRepository.save(game);
    }

    public List<Game> save(List<Game> games) {
        String groupId = UUID.randomUUID().toString();
        games.forEach(game -> game.setGroupId(groupId));
        return gamesRepository.saveAll(games);
    }

    public List<Game> findByGroupId(String groupId) {
        return gamesRepository.findByGroupId(groupId).stream().peek(game -> game.setCounter(game.getUsers().size())).collect(Collectors.toList());
    }

    public Map<String, List<Game>> findLists() {
        return gamesRepository.findAll().stream().collect(Collectors.groupingBy(game -> game.getGroupId()));
    }

    public void deleteGroupById(String groupId) {
        gamesRepository.deleteByGroupId(groupId);
    }
}
