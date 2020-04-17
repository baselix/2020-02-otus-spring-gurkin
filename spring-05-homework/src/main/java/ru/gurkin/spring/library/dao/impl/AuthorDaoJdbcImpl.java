package ru.gurkin.spring.library.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.AuthorDao;
import ru.gurkin.spring.library.dao.mapper.AuthorRowMapper;
import ru.gurkin.spring.library.model.Author;

@Repository
public class AuthorDaoJdbcImpl implements AuthorDao {

	private final NamedParameterJdbcOperations jdbc;

	private static final String AUTHOR_QUERY = "SELECT id, name FROM AUTHORS ";
	private static final String AUTHOR_WHERE_NAME = "WHERE name LIKE :name";
	private static final String AUTHOR_WHERE_ID = "WHERE id = :id";

	private static final String ID_PARAM = "id";
	private static final String NAME_PARAM = "name";

	private static final String AUTHOR_NOT_FOUND = "Не удалось найти автора";

	private static final String CREATE_AUTHOR_QUERY = "insert into authors (`id`, `name`) values (default, :name)";
	private static final String UPDATE_AUTHOR_QUERY = "update authors set name = :name where id = :id";
	private static final String DELETE_AUTHOR_QUERY = "delete from authors where id = :id";

	public AuthorDaoJdbcImpl(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbc = jdbcOperations;
	}

	@Transactional
	@Override
	public List<Author> getAll() {
		return jdbc.query(AUTHOR_QUERY, new AuthorRowMapper());
	}

	@Transactional
	@Override
	public Author getById(Long id) {
		Map<String, Object> params = Collections.singletonMap(ID_PARAM, id);
		return jdbc.queryForObject(AUTHOR_QUERY + AUTHOR_WHERE_ID, params, new AuthorRowMapper());
	}

	@Transactional
	@Override
	public Author create(Author author) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(NAME_PARAM, author.getName());
		jdbc.update(CREATE_AUTHOR_QUERY, params);
		return jdbc.queryForObject(AUTHOR_QUERY + AUTHOR_WHERE_NAME, params, new AuthorRowMapper());
	}

	@Transactional
	@Override
	public Author update(Author author) {
		Author old = getById(author.getId());
		if (old == null) {
			throw new IllegalStateException(AUTHOR_NOT_FOUND);
		}
		if (author != null && !old.getName().equals(author.getName())) {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue(NAME_PARAM, author.getName());
			params.addValue(ID_PARAM, author.getId());
			jdbc.update(UPDATE_AUTHOR_QUERY, params);
		}
		return author;
	}

	@Transactional
	@Override
	public void delete(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(ID_PARAM, id);
		jdbc.update(DELETE_AUTHOR_QUERY, params);
	}

	@Transactional
	@Override
	public List<Author> find(String nameFilter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(NAME_PARAM, "%" + nameFilter + "%");
		return jdbc.query(AUTHOR_QUERY + AUTHOR_WHERE_NAME, params, new AuthorRowMapper());
	}
}
