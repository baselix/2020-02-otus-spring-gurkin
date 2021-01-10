package ru.gurkin.spring.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gurkin.spring.library.model.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long>{
    List<Genre> findByTitle(String title);
}
