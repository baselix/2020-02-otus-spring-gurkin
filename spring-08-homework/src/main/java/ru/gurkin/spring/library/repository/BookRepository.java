package ru.gurkin.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gurkin.spring.library.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitleLike(String titleFilter);
    Book findByTitle(String title);
}
