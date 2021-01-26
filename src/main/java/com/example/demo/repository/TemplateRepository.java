package com.example.demo.repository;

import com.example.demo.model.dao.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TemplateRepository extends MongoRepository<Template, String> {

    Optional<Template> findByName(String name);
}
