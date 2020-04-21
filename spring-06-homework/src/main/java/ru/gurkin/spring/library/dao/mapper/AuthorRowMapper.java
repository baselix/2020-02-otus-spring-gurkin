package ru.gurkin.spring.library.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.gurkin.spring.library.model.Author;

public class AuthorRowMapper implements RowMapper<Author> {

	private static final String AUTHOR_ID = "id";
	private static final String AUTHOR_NAME = "name";

	@Override
	public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
		long id = rs.getLong(AUTHOR_ID);
		String name = rs.getString(AUTHOR_NAME);
		return new Author(id, name);
	}

}
