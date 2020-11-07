package ru.gurkin.spring.library.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.impl.AuthorDaoJpaImpl;
import ru.gurkin.spring.library.dao.impl.BookDaoJpaImpl;
import ru.gurkin.spring.library.dao.impl.GenreDaoJpaImpl;
import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ActiveProfiles("test")
@DataJpaTest
@Import({BookDaoJpaImpl.class, AuthorDaoJpaImpl.class, GenreDaoJpaImpl.class})
@DisplayName("Класс dao для книг корректно ")
class BookDaoTest {

	private static final String TITLE_FILTER = "test book";

	@PersistenceContext
	private final EntityManager entityManager;

	@Autowired
	public BookDaoJpaImpl bookDao;
	@Autowired
	public AuthorDaoJpaImpl authorDao;
	@Autowired
	public GenreDaoJpaImpl genreDao;

	@Autowired
	BookDaoTest(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Test
	@DisplayName("получает все книги")
	void getAllTest() {
		List<Book> allBooks = bookDao.getAll();
		for(Book book : allBooks) {
			System.out.println(book);
		}
		assertEquals(4, allBooks.size());
	}

	@Test
	@DisplayName("получает книгу по названию")
	void geteByTitleTest() {
		Book foundedBook = bookDao.getByTitle("test book 3");
		assertEquals("test book 3", foundedBook.getTitle());
	}

	@Test
	@DisplayName("создает и получает книгу")
	void createAndGetTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorDao.getById(1L);
		newBook.getAuthors().add(author);
		Genre genre = genreDao.getById(1L);
		newBook.getGenres().add(genre);
		newBook = bookDao.create(newBook);
		assertFalse(newBook.getAuthors().isEmpty());
		assertFalse(newBook.getGenres().isEmpty());
		Book foundedBook = bookDao.getById(newBook.getId());
		assertEquals(newBook, foundedBook);
		assertFalse(foundedBook.getAuthors().isEmpty());
		assertFalse(foundedBook.getGenres().isEmpty());
	}

	@Test
	@DisplayName("обновляет книгу")
	void updateTest() {
		Book book = bookDao.getById(1L);
		Author author = new Author();
		author.setId(2L);
		author.setName("test author 2");
		book.getAuthors().add(author);
		Genre genre = new Genre(1L, "test genre 1");
		book.getGenres().remove(genre);
		String title = "new title";
		book.setTitle(title);
		Book updatedBook = bookDao.update(book);
		assertEquals(title, updatedBook.getTitle());
		assertTrue(updatedBook.getAuthors().contains(author));
		assertFalse(updatedBook.getGenres().contains(genre));
	}

	@Test
	@DisplayName("ищет книги")
	void findTest() {
		List<Book> books = bookDao.search(TITLE_FILTER);
		for (Book book : books) {
			assertTrue(book.getTitle().contains(TITLE_FILTER));
		}
	}

	@Test
	@DisplayName("удаляет книги")
	@Transactional
	void deleteTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorDao.getById(1L);
		newBook.getAuthors().add(author);
		Genre genre = genreDao.getById(1L);
		newBook.getGenres().add(genre);
		newBook = bookDao.create(newBook);
		flushAndClear();

		long id = newBook.getId();
		boolean isIdFound = false;
		List<Book> books = bookDao.getAll();
		for (Book book : books) {
			if (book.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		bookDao.delete(id);

		flushAndClear();

		isIdFound = false;
		books = bookDao.getAll();
		for (Book book : books) {
			if (book.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}

	void flushAndClear() {
		entityManager.flush();
		entityManager.clear();
	}
}
