package ru.gurkin.spring.library.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.dao.GenreDao;
import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.service.GenreService;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Service
public class GenreServiceImpl implements GenreService{

	private final GenreDao dao;

	public GenreServiceImpl(GenreDao dao) {
		this.dao = dao;
	}

	@Transactional
	@Override
	public List<Genre> getAll() {
		return dao.getAll();
	}

	@Transactional
	@Override
	public List<Genre> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return dao.find(titleFilter);
	}

	@Transactional
	@Override
	public Genre getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.getById(id);
	}

	@Transactional
	@Override
	public Genre create(Genre genre) {
		checkNotNull(genre, GENRE_ERROR);
		checkArgument(genre.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(genre.getTitle()), TITLE_ERROR);
		return dao.create(genre);
	}

	@Transactional
	@Override
	public Genre update(Genre genre) {
		checkNotNull(genre, GENRE_ERROR);
		checkArgument(genre.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(genre.getTitle()), TITLE_ERROR);
		return dao.update(genre);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		dao.delete(id);
	}

}
