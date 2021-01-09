package com.example.demo.repository;

import com.example.demo.model.dao.AppUser;
import org.bson.types.Binary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<AppUser, String> {

    Optional<AppUser> findByEmail(String email);

    AppUser findByAvatar(Binary avatar);
















}
