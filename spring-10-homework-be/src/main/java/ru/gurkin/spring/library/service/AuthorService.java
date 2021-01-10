package ru.gurkin.spring.library.service;

import java.util.List;

import ru.gurkin.spring.library.model.Author;

public interface AuthorService {
	List<Author> getAll();

	List<Author> search(String titleFilter);

	Author getById(Long id);

	Author create(Author model);

	Author update(Author model);

	void delete(Long id);
}
