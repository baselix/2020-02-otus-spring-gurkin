package ru.gurkin.spring.library.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.model.Author;


@Transactional
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>{

	List<Author> findByNameLikeIgnoreCase(String name);
}
