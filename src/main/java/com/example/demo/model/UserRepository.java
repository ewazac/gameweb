package com.example.demo.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<AppUser, String> {

    AppUser findUserByEmail(String email);

    AppUser findUserById(String id);

    AppUser findByAvatar(Binary avatar);













}
