package ru.gurkin.spring.library.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Genre;

public class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {

	private static final String BOOK_ID = "B_ID";
	private static final String BOOK_TITLE = "B_TITLE";
	private static final String AUTHOR_ID = "A_ID";
	private static final String AUTHOR_NAME = "A_NAME";
	private static final String GENRE_ID = "G_ID";
	private static final String GENRE_TITLE = "G_TITLE";

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Long, Set<Author>> booksAuthors = Maps.newHashMap();
		Map<Long, Set<Genre>> booksGenres = Maps.newHashMap();
		Map<Long, Book> booksMap = Maps.newHashMap();
		while (rs.next()) {
			// получаем данные строки
			Long bookId = rs.getLong(BOOK_ID);
			Long authorId = rs.getLong(AUTHOR_ID);
			Long genreId = rs.getLong(GENRE_ID);
			String bookTitle = rs.getString(BOOK_TITLE);
			String authorName = rs.getString(AUTHOR_NAME);
			String genreTitle = rs.getString(GENRE_TITLE);
			Book book = new Book(bookId, bookTitle);
			booksMap.put(bookId, book);
			// раскладываем данные по мапам
			if (authorId != 0 && authorName != null) {
				Author author = new Author(authorId, authorName);
				if (booksAuthors.containsKey(bookId)) {
					booksAuthors.get(bookId).add(author);
				} else {
					booksAuthors.put(bookId, Sets.newHashSet(author));
				}
			}
			if (genreId != 0 && genreTitle != null) {
				Genre genre = new Genre(genreId, genreTitle);
				if (booksGenres.containsKey(bookId)) {
					booksGenres.get(bookId).add(genre);
				} else {
					booksGenres.put(bookId, Sets.newHashSet(genre));
				}
			}
		}
		// все книжки замаплены, собираем корректные модели
		for (Long bookId : booksMap.keySet()) {
			if (booksAuthors.containsKey(bookId)) {
				booksMap.get(bookId).getAuthors().addAll(booksAuthors.get(bookId));
			}
			if (booksGenres.containsKey(bookId)) {
				booksMap.get(bookId).getGenres().addAll(booksGenres.get(bookId));
			}
		}
		return Lists.newArrayList(booksMap.values());
	}

}
