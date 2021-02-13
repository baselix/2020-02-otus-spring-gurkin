package ru.gurkin.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gurkin.spring.library.model.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByTitle(String title);
}
