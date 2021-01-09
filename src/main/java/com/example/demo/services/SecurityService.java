package com.example.demo.services;

import com.example.demo.model.dao.Forum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final ForumService forumService;
    private final UserService userService;


    public boolean hasAccessToForum(String id) {
        return forumService.getById(id).getCreatedBy().equals(userService.getCurrentUser().getEmail());
    }
}
