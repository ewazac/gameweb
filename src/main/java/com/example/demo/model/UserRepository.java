package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<AppUser, String> {

    AppUser findUserByEmail(String email);

}
