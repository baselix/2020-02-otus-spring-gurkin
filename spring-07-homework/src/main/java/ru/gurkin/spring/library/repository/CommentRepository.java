package ru.gurkin.spring.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;

import java.util.List;

@Transactional
@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{
    List<Comment> findByBook(Book book);
}
