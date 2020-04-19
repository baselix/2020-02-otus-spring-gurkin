package ru.gurkin.spring.library.dao.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.BookDao;
import ru.gurkin.spring.library.dao.mapper.BookResultSetExtractor;
import ru.gurkin.spring.library.dao.mapper.BookRowMapper;
import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Genre;

@Repository
public class BookDaoJdbcImpl implements BookDao {

	private static final String SIMPLE_BOOK_QUERY = "SELECT b.id, b.title FROM BOOKS b ";
	private static final String BOOK_QUERY = "SELECT b.ID AS B_ID, " + "b.TITLE AS B_TITLE, " + "a.ID AS A_ID, "
			+ "a.NAME AS A_NAME, " + "g.ID AS G_ID, " + "g.TITLE AS G_TITLE " + "FROM BOOKS b "
			+ "LEFT JOIN BOOK_AUTHOR ON b.ID = BOOK_AUTHOR.BOOK_ID "
			+ "LEFT JOIN AUTHORS a ON BOOK_AUTHOR.AUTHOR_ID  = a.ID "
			+ "LEFT JOIN BOOK_GENRE ON b.ID = BOOK_GENRE.BOOK_ID "
			+ "LEFT JOIN GENRES g ON BOOK_GENRE.GENRE_ID  = g.ID ";
	private static final String BOOK_WHERE_TITLE_LIKE = "WHERE b.TITLE LIKE :title";
	private static final String BOOK_WHERE_TITLE_EQUALS = "WHERE b.TITLE = :title";
	private static final String BOOK_WHERE_ID = "WHERE b.ID = :id";

	private static final String CREATE_BOOK_QUERY = "insert into books (`id`, `title`) values (default, :title)";
	private static final String LINK_AUTHOR_QUERY = "insert into book_author (`book_id`, `author_id`) values (:book_id, :author_id)";
	private static final String LINK_GENRE_QUERY = "insert into book_genre (`book_id`, `genre_id`) values (:book_id, :genre_id)";
	private static final String UPDATE_BOOK_QUERY = "update books set title = :title where id = :id";

	private static final String DELETE_BOOK_QUERY = "delete from books where id = :id";
	private static final String DELETE_GENRE_LINK_QUERY = "delete from book_genre where book_id = :id";
	private static final String DELETE_OLD_GENRE_LINK_QUERY = "delete from book_genre where book_id = :book_id and genre_id = :genre_id";
	private static final String DELETE_AUTHOR_LINK_QUERY = "delete from book_author where book_id = :id";
	private static final String DELETE_OLD_AUTHOR_LINK_QUERY = "delete from book_author where book_id = :book_id and author_id = :author_id";

	private static final String TOO_MANY_BOOKS_TITLE = "Найдено более одной книги с названием %s";
	private static final String TOO_MANY_BOOKS_ID = "Найдено более одной книги для идентификатора %d";
	private static final String BOOK_NOT_FOUND = "Не удалось найти книгу с идентификатором %d";

	private static final String ID_PARAM = "id";
	private static final String TITLE_PARAM = "title";
	private static final String BOOK_ID_PARAM = "book_id";
	private static final String GENRE_ID_PARAM = "genre_id";
	private static final String AUTHOR_ID_PARAM = "author_id";

	private final NamedParameterJdbcOperations jdbc;

	public BookDaoJdbcImpl(NamedParameterJdbcOperations jdbcOperations) {
		this.jdbc = jdbcOperations;
	}

	@Transactional
	@Override
	public List<Book> getAll() {
		return jdbc.query(BOOK_QUERY, new BookResultSetExtractor());
	}

	@Transactional
	@Override
	public Book getById(Long id) {
		Map<String, Object> params = Collections.singletonMap(ID_PARAM, id);
		List<Book> books = jdbc.query(BOOK_QUERY + BOOK_WHERE_ID, params, new BookResultSetExtractor());
		if (books.size() == 1) {
			return books.get(0);
		} else if (books.isEmpty()) {
			return null;
		} else {
			throw new IllegalStateException(String.format(TOO_MANY_BOOKS_ID, id));
		}
	}

	@Transactional
	@Override
	public Book create(Book book) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(TITLE_PARAM, book.getTitle());
		jdbc.update(CREATE_BOOK_QUERY, params);
		Book createdBook = jdbc.queryForObject(SIMPLE_BOOK_QUERY + BOOK_WHERE_TITLE_EQUALS, params,
				new BookRowMapper());
		book.setId(createdBook.getId());
		for (Author author : book.getAuthors()) {
			params = new MapSqlParameterSource();
			params.addValue(BOOK_ID_PARAM, book.getId());
			params.addValue(AUTHOR_ID_PARAM, author.getId());
			jdbc.update(LINK_AUTHOR_QUERY, params);
		}
		for (Genre genre : book.getGenres()) {
			params = new MapSqlParameterSource();
			params.addValue(BOOK_ID_PARAM, book.getId());
			params.addValue(GENRE_ID_PARAM, genre.getId());
			jdbc.update(LINK_GENRE_QUERY, params);
		}
		return book;
	}

	@Transactional
	@Override
	public Book update(Book book) {
		// получить старую модель
		Book oldBook = getById(book.getId());
		if (oldBook == null) {
			throw new IllegalStateException(BOOK_NOT_FOUND);
		}
		// обновить название книги
		if (!book.getTitle().equals(oldBook.getTitle())) {
			updateBook(book);
		}
		// обновить связки книги с авторами
		addAuthorsToBook(book, oldBook);
		deleteAuthorsFormBook(book, oldBook);
		// обновить связки книги с жанрами
		addGenresToBook(book, oldBook);
		deleteGenresFormBook(book, oldBook);
		return getById(book.getId());
	}

	private void updateBook(Book book) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(TITLE_PARAM, book.getTitle());
		params.addValue(ID_PARAM, book.getId());
		jdbc.update(UPDATE_BOOK_QUERY, params);
	}

	private void addAuthorsToBook(Book book, Book oldBook) {
		// получить списки авторов для добавления
		Set<Author> addAuthors = new HashSet<Author>(book.getAuthors());
		addAuthors.removeAll(oldBook.getAuthors());
		if (!addAuthors.isEmpty()) {
			for (Author author : addAuthors) {
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue(BOOK_ID_PARAM, book.getId());
				params.addValue(AUTHOR_ID_PARAM, author.getId());
				jdbc.update(LINK_AUTHOR_QUERY, params);
			}
		}
	}

	private void deleteAuthorsFormBook(Book book, Book oldBook) {
		// получить списки авторов для удаления
		Set<Author> deleteAuthors = new HashSet<Author>(oldBook.getAuthors());
		deleteAuthors.removeAll(book.getAuthors());
		if (!deleteAuthors.isEmpty()) {
			for (Author author : deleteAuthors) {
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue(BOOK_ID_PARAM, book.getId());
				params.addValue(AUTHOR_ID_PARAM, author.getId());
				jdbc.update(DELETE_OLD_AUTHOR_LINK_QUERY, params);
			}
		}
	}

	private void addGenresToBook(Book book, Book oldBook) {
		// получить списки жанров для добавления
		Set<Genre> addGenres = new HashSet<Genre>(book.getGenres());
		addGenres.removeAll(oldBook.getGenres());
		if (!addGenres.isEmpty()) {
			for (Genre genre : addGenres) {
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue(BOOK_ID_PARAM, book.getId());
				params.addValue(GENRE_ID_PARAM, genre.getId());
				jdbc.update(LINK_GENRE_QUERY, params);
			}
		}
	}

	private void deleteGenresFormBook(Book book, Book oldBook) {
		// получить списки жанров для удаления
		Set<Genre> deleteGenres = new HashSet<Genre>(oldBook.getGenres());
		deleteGenres.removeAll(book.getGenres());
		if (!deleteGenres.isEmpty()) {
			for (Genre genre : deleteGenres) {
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue(BOOK_ID_PARAM, book.getId());
				params.addValue(GENRE_ID_PARAM, genre.getId());
				jdbc.update(DELETE_OLD_GENRE_LINK_QUERY, params);
			}
		}
	}

	@Transactional
	@Override
	public void delete(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(ID_PARAM, id);
		jdbc.update(DELETE_AUTHOR_LINK_QUERY, params);
		jdbc.update(DELETE_GENRE_LINK_QUERY, params);
		jdbc.update(DELETE_BOOK_QUERY, params);
	}

	@Transactional
	@Override
	public Book getByTitle(String title) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(TITLE_PARAM, title);
		List<Book> books = jdbc.query(BOOK_QUERY + BOOK_WHERE_TITLE_EQUALS, params, new BookResultSetExtractor());
		if (books.size() == 1) {
			return books.get(0);
		} else if (books.isEmpty()) {
			return null;
		} else {
			throw new IllegalStateException(String.format(TOO_MANY_BOOKS_TITLE, title));
		}
	}

	@Transactional
	@Override
	public List<Book> search(String titleFilter) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(TITLE_PARAM, "%" + titleFilter + "%");
		return jdbc.query(BOOK_QUERY + BOOK_WHERE_TITLE_LIKE, params, new BookResultSetExtractor());
	}
}
