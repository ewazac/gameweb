package com.example.demo.repository;

import com.example.demo.model.dao.User;
import org.bson.types.Binary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    User findByAvatar(Binary avatar);

    Optional<User> findByActivateCode(String activateCode);

    List<User> findByNewsletterIsTrue();


    List<User> findByIdIn(List<String> userIds);
}
