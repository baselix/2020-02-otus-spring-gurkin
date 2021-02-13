package ru.gurkin.spring.library.service;

import java.util.List;

import ru.gurkin.spring.library.model.Genre;

public interface GenreService {
	List<Genre> getAll();

	List<Genre> search(String titleFilter);

	Genre getById(Long id);

	Genre create(Genre model);

	Genre update(Genre model);

	void delete(Long id);
}
