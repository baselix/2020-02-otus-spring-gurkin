package ru.gurkin.spring.library.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.repository.GenreRepository;
import ru.gurkin.spring.library.service.GenreService;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Service
public class GenreServiceImpl implements GenreService{

	private final GenreRepository repository;

	public GenreServiceImpl(GenreRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Genre> getAll() {
		return repository.findAll();
	}

	@Override
	public List<Genre> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return repository.findByTitle(titleFilter);
	}

	@Override
	public Genre getById(String id) {
		checkNotNull(id, ID_ERROR);
		return repository.findById(id).orElse(null);
	}

	@Override
	public Genre create(Genre genre) {
		checkNotNull(genre, GENRE_ERROR);
		checkArgument(genre.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(genre.getTitle()), TITLE_ERROR);
		return repository.save(genre);
	}

	@Override
	public Genre update(Genre genre) {
		checkNotNull(genre, GENRE_ERROR);
		checkArgument(genre.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(genre.getTitle()), TITLE_ERROR);
		return repository.save(genre);
	}

	@Override
	public void delete(String id) {
		checkNotNull(id, ID_ERROR);
		repository.deleteById(id);
	}
}
