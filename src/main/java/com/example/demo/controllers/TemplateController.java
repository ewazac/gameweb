package com.example.demo.controllers;

import com.example.demo.model.dao.Template;
import com.example.demo.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateRepository templateRepository;

    @PostMapping()
    public Template save(@RequestBody Template template) {
        return templateRepository.save(template);
    }

//    @PostMapping("/{templateId}")
//    public void saveFile(MultipartFile multipartFile, @PathVariable String templateId) {
//        Template template = templateRepository.findTemplateById(templateId);
//        template.setBody(multipartFile.toString());
//        templateRepository.save(template);
//
//    }

}
