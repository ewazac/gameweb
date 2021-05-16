package com.example.demo.mapper;

import com.example.demo.model.dao.News;
import com.example.demo.model.dto.NewsDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsMapper {

    public News toDao(NewsDto newsDto) {
        return News.builder()
                .id(newsDto.getId())
                .title(newsDto.getTitle())
                .sendMail(newsDto.isSendMail())
                .description(newsDto.getDescription())
                .image(newsDto.getImage())
                .body(newsDto.getBody())
                .build();
    }

    public NewsDto toDto(News news) {
        return NewsDto.builder()
                .id(news.getId())
                .description(news.getDescription())
                .image(news.getImage())
                .title(news.getTitle())
                .body(news.getBody())
                .build();
    }

    public List<NewsDto> toListDto(List<News> newsList) {
        return newsList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }



}
