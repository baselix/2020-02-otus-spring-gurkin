package ru.gurkin.spring.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBook(Book book);
}
