package com.example.demo.services;

import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Answer;
import com.example.demo.model.dao.Forum;
import com.example.demo.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final UserService userService;

    /**Returns list of all threads*/
    public List<Forum> getAll() {
        return forumRepository.findAll();
    }

    /**Saves thread*/
    public Forum save(Forum forum) {
        return forumRepository.save(forum);
    }

    /**Updates thread*/
    public Forum update(Forum forum, String id) {
        Forum forumDb = getById(id);
        forumDb.setName(forum.getName());
        return save(forumDb);
    }

    /**Deletes thread*/
    public void deleteById(String id) {
        forumRepository.deleteById(id);
    }

    /**Returns thread by id*/
    public Forum getById(String id) {
        return forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    /**Deletes answer in thread by index*/
    public Forum deleteAnswerByIndex(int index, String id) {
        Forum forum = getById(id);
        forum.getAnswers().remove(index);
        return save(forum);
    }

    /**Adds answer to thread*/
    public Forum addAnswer(Answer answer, String id) {
        Forum forum = getById(id);
        answer.setUserId(userService.getCurrentUser().getId());
        forum.getAnswers().add(answer);
        return save(forum);
    }

}
