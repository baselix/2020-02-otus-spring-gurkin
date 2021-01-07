package ru.gurkin.spring.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gurkin.spring.library.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitleLike(String titleFilter);
    Book findByTitle(String title);
}
