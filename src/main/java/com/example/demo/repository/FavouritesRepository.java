package com.example.demo.repository;

import com.example.demo.model.dao.Favourites;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FavouritesRepository extends MongoRepository<Favourites, String> {

    List<Favourites> findByUserId(String id);

}
