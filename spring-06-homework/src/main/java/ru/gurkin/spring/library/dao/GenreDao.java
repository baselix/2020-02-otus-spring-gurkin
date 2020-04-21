package ru.gurkin.spring.library.dao;

import java.util.List;

import ru.gurkin.spring.library.model.Genre;

public interface GenreDao {
	List<Genre> getAll();

	List<Genre> find(String titleFilter);

	Genre getById(Long id);

	Genre create(Genre model);

	Genre update(Genre model);

	void delete(Long id);
}
