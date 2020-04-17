package ru.gurkin.spring.library.dao;

import java.util.List;

import ru.gurkin.spring.library.model.Author;

public interface AuthorDao {
	List<Author> getAll();

	List<Author> find(String nameFilter);

	Author getById(Long id);

	Author create(Author model);

	Author update(Author model);

	void delete(Long id);
}
