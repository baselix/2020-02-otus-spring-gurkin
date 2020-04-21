package ru.gurkin.spring.library.service;

import java.util.List;

import ru.gurkin.spring.library.model.Book;

public interface BookService {
	List<Book> getAll();

	List<Book> search(String titleFilter);

	Book getById(Long id);

	Book getByTitle(String title);

	Book create(Book model);

	Book update(Book model);

	void delete(Long id);
}
