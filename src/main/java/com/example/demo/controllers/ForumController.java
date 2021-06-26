package com.example.demo.controllers;

import com.example.demo.mapper.ForumMapper;
import com.example.demo.model.dao.Answer;
import com.example.demo.model.dto.ForumDto;
import com.example.demo.services.ForumService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/forums")
@CrossOrigin(origins = {"https://gameweb.projektstudencki.pl", "http://localhost:4200"}, allowCredentials = "true")
@RequiredArgsConstructor
public class ForumController {

    private final ForumService forumService;
    private final ForumMapper forumMapper;

    @GetMapping()
    @ApiOperation(value = "Returns list of all forum threads")
    public List<ForumDto> getListForum() {
        return forumMapper.toListDto(forumService.getAll());
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for adding new thread. Only for logged in users")
    public ForumDto saveForum(@RequestBody ForumDto forumDTO) {
        return forumMapper.toDto(forumService.save(forumMapper.toDao(forumDTO)));
    }
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() && @securityService.hasAccessToForum(#id)")
    @ApiOperation(value = "Method for updating thread")
    public ForumDto updateForum(@RequestBody ForumDto forumDTO, @PathVariable String id) {
        return forumMapper.toDto(forumService.update(forumMapper.toDao(forumDTO), id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for deleting thread")
    public void deleteForumById(@PathVariable String id) {
        forumService.deleteById(id);
    }

    @DeleteMapping("/{id}/answer/{index}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for deleting answer in thead")
    public ForumDto deleteAnswerByIndex(@PathVariable String id, @PathVariable int index) {
        return forumMapper.toDto(forumService.deleteAnswerByIndex(index, id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Method for adding answer to thread")
    public ForumDto addAnswerToForum(@PathVariable String id, @RequestBody Answer answer) {
        return forumMapper.toDto(forumService.addAnswer(answer, id));
    }
}
