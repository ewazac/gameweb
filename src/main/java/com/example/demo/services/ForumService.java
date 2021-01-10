package com.example.demo.services;

import com.example.demo.exeption.EntityNotFoundException;
import com.example.demo.model.dao.Answer;
import com.example.demo.model.dao.Forum;
import com.example.demo.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final UserService userService;

    public List<Forum> getAll() {
        return forumRepository.findAll();
    }


    public Forum save(Forum forum) {
        return forumRepository.save(forum);
    }


    public Forum update(Forum forum, String id) {
        Forum forumDb = getById(id);
        forumDb.setName(forum.getName());
        return save(forumDb);
    }

    public void deleteById(String id) {
        forumRepository.deleteById(id);
    }

    public Forum getById(String id) {
        return forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Forum deleteAnswerByIndex(int index, String id) {
        Forum forum = getById(id);
        forum.getAnswers().remove(index);
        return save(forum);
    }

    public Forum addAnswer(Answer answer, String id) {
        Forum forum = getById(id);
        answer.setUsername(userService.getCurrentUser().getEmail());
        forum.getAnswers().add(answer);
        return save(forum);
    }

}
