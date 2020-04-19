package ru.gurkin.spring.library.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Genre;

@SpringBootTest(properties = { InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false" })
@Transactional
@ActiveProfiles("test")
@DisplayName("Класс dao для книг корректно ")
class BookDaoTest {

	private static final String TITLE_FILTER = "test book";

	@Autowired
	BookDao bookDao;

	@Test
	@DisplayName("получает все книги")
	void getAllTest() {
		List<Book> allAuthors = bookDao.getAll();
		assertEquals(4, allAuthors.size());
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
		newBook = bookDao.create(newBook);
		Book foundedBook = bookDao.getById(newBook.getId());
		assertEquals(newBook, foundedBook);
	}

	@Test
	@DisplayName("обновляет книгу")
	void updateTest() {
		Book book = bookDao.getById(1L);
		Author author = new Author(2L, "test author 2");
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
	void deleteTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		newBook = bookDao.create(newBook);
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
}
