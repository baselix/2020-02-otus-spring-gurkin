package ru.gurkin.spring.library.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.GenreDao;
import ru.gurkin.spring.library.dao.mapper.GenreRowMapper;
import ru.gurkin.spring.library.model.Genre;

@Repository
public class GenreDaoJdbcImpl implements GenreDao {

	private final NamedParameterJdbcOperations jdbc;

	private static final String GENRE_QUERY = "SELECT id, title FROM GENRES ";
	private static final String GENRE_WHERE_TITLE = "WHERE title LIKE :title";
	private static final String GENRE_WHERE_ID = "WHERE id = :id";

	private static final String ID_PARAM = "id";
	private static final String TITLE_PARAM = "title";

	private static final String GENRE_NOT_FOUND = "Не удалось найти жанр";

	private static final String CREATE_GENRE_QUERY = "insert into genres (`id`, `title`) values (default, :title)";
	private static final String UPDATE_GENRE_QUERY = "update genres set title = :title where id = :id";
	private static final String DELETE_GENRE_QUERY = "delete from genres where id = :id";

	public GenreDaoJdbcImpl(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbc = jdbcOperations;
	}

	@Transactional
	@Override
	public List<Genre> getAll() {
		return jdbc.query(GENRE_QUERY, new GenreRowMapper());
	}

	@Transactional
	@Override
	public Genre getById(Long id) {
		Map<String, Object> params = Collections.singletonMap(ID_PARAM, id);
		return jdbc.queryForObject(GENRE_QUERY + GENRE_WHERE_ID, params, new GenreRowMapper());
	}

	@Transactional
	@Override
	public Genre create(Genre genre) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(TITLE_PARAM, genre.getTitle());
		jdbc.update(CREATE_GENRE_QUERY, params);
		return jdbc.queryForObject(GENRE_QUERY + GENRE_WHERE_TITLE, params, new GenreRowMapper());
	}

	@Transactional
	@Override
	public Genre update(Genre genre) {
		Genre old = getById(genre.getId());
		if (old == null) {
			throw new IllegalStateException(GENRE_NOT_FOUND);
		}
		if (!old.getTitle().equals(genre.getTitle())) {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue(TITLE_PARAM, genre.getTitle());
			params.addValue(ID_PARAM, genre.getId());
			jdbc.update(UPDATE_GENRE_QUERY, params);
		}
		return genre;
	}

	@Transactional
	@Override
	public void delete(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(ID_PARAM, id);
		jdbc.update(DELETE_GENRE_QUERY, params);
	}

	@Transactional
	@Override
	public List<Genre> find(String titleFilter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(TITLE_PARAM, "%" + titleFilter + "%");
		return jdbc.query(GENRE_QUERY + GENRE_WHERE_TITLE, params, new GenreRowMapper());
	}
}
