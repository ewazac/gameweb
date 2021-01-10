package com.example.demo.mapper;

import com.example.demo.model.dao.Forum;
import com.example.demo.model.dto.ForumDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ForumMapper {

    public Forum toDao(ForumDto forumDTO) {
        return Forum.builder()
                .id(forumDTO.getId())
                .answers(forumDTO.getAnswers())
                .name(forumDTO.getName())
                .build();
    }

    public ForumDto toDto(Forum forum) {
        return ForumDto.builder()
                .answers(forum.getAnswers())
                .id(forum.getId())
                .name(forum.getName())
                .build();
    }

    public List<ForumDto> toListDto(List<Forum> forumList) {
        return forumList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
