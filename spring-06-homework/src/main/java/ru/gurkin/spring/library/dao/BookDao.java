package ru.gurkin.spring.library.dao;

import java.util.List;

import ru.gurkin.spring.library.model.Book;

public interface BookDao {
	List<Book> getAll();

	List<Book> search(String titleFilter);

	Book getByTitle(String title);

	Book getById(Long id);

	Book create(Book model);

	Book update(Book model);

	void delete(Long id);
}
