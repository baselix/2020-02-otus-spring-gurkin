package ru.gurkin.spring.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{
    List<Comment> findByBook(Book book);
}
