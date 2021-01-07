package ru.gurkin.spring.library.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.repository.GenreRepository;
import ru.gurkin.spring.library.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService{

	private final GenreRepository dao;

	public GenreServiceImpl(GenreRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Genre> getAll() {
		return (List<Genre>) dao.findAll();
	}

	@Override
	public List<Genre> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return dao.findByTitle(titleFilter);
	}

	@Override
	public Genre getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.findById(id).orElse(null);
	}

	@Override
	public Genre create(Genre genre) {
		checkNotNull(genre, GENRE_ERROR);
		checkArgument(genre.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(genre.getTitle()), TITLE_ERROR);
		return dao.save(genre);
	}

	@Override
	public Genre update(Genre genre) {
		checkNotNull(genre, GENRE_ERROR);
		checkArgument(genre.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(genre.getTitle()), TITLE_ERROR);
		return dao.save(genre);
	}

	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		dao.deleteById(id);
	}

}
