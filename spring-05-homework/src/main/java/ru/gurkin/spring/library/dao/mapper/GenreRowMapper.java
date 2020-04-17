package ru.gurkin.spring.library.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.gurkin.spring.library.model.Genre;

public class GenreRowMapper implements RowMapper<Genre> {

	private static final String GENRE_ID = "id";
	private static final String GENRE_TITLE = "title";

	@Override
	public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
		long id = rs.getLong(GENRE_ID);
		String title = rs.getString(GENRE_TITLE);
		return new Genre(id, title);
	}

}
