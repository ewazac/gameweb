package com.example.demo.controllers;

import com.example.demo.model.dao.Template;
import com.example.demo.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateRepository templateRepository;

    @PostMapping()
    public Template save(@RequestBody Template template) {
        return templateRepository.save(template);
    }


}
