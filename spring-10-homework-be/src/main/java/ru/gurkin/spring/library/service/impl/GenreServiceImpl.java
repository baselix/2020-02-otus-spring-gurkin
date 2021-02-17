package ru.gurkin.spring.library.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.repository.GenreRepository;
import ru.gurkin.spring.library.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService{

	private final GenreRepository repository;

	public GenreServiceImpl(GenreRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Genre> getAll() {
		return (List<Genre>) repository.findAll();
	}

	@Override
	public List<Genre> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return repository.findByTitle(titleFilter);
	}

	@Override
	public Genre getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return repository.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public Genre create(Genre genre) {
		checkNotNull(genre, GENRE_ERROR);
		checkArgument(genre.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(genre.getTitle()), TITLE_ERROR);
		return repository.save(genre);
	}

	@Transactional
	@Override
	public Genre update(Genre genre) {
		checkNotNull(genre, GENRE_ERROR);
		checkArgument(genre.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(genre.getTitle()), TITLE_ERROR);
		return repository.save(genre);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		repository.deleteById(id);
	}
}
