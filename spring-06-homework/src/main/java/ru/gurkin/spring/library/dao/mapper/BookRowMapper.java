package ru.gurkin.spring.library.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.gurkin.spring.library.model.Book;

public class BookRowMapper implements RowMapper<Book> {

	private static final String BOOK_ID = "id";
	private static final String BOOK_TITLE = "title";

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setId(rs.getLong(BOOK_ID));
		book.setTitle(rs.getString(BOOK_TITLE));
		return book;
	}

}
