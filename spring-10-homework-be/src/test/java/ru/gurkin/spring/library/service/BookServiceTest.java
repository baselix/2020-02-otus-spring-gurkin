package ru.gurkin.spring.library.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.repository.BookRepository;
import ru.gurkin.spring.library.service.impl.BookServiceImpl;

import static ru.gurkin.spring.library.model.ErrorConstants.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
@DisplayName("Класс сервиса для книг корректно ")
class BookServiceTest {

	@MockBean
	private BookRepository repository;

	@InjectMocks
	private BookServiceImpl service;

	public <T extends Throwable> void checkExeption(Class<T> exceptionClass, String expectedMessage,
			Executable executable) {
		Throwable exception = assertThrows(exceptionClass, executable);
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("проверяет параметры создаваемой книги")
	void createBookVerificationTest() {
		checkExeption(NullPointerException.class, BOOK_ERROR, () -> service.create(null));
		Book book = new Book();
		book.setId(1L);
		checkExeption(IllegalArgumentException.class, NULL_ID_ERROR, () -> service.create(book));
		book.setId(null);
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.create(book));
		book.setTitle("");
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.create(book));
		book.setTitle("new title");
		checkExeption(IllegalArgumentException.class, BOOK_NO_AUTHOR_ERROR, () -> service.create(book));
		book.getAuthors().add(new Author());
		checkExeption(IllegalArgumentException.class, BOOK_NO_GENRE_ERROR, () -> service.create(book));
	}

	@Test
	@DisplayName("проверяет параметры при удалении книги")
	void deleteBookVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.delete(null));
	}

	@Test
	@DisplayName("проверяет параметры обновляемой книги")
	void updateBookVerificationTest() {
		checkExeption(NullPointerException.class, BOOK_ERROR, () -> service.update(null));
		Book book = new Book();
		checkExeption(IllegalArgumentException.class, NOT_NULL_ID_ERROR, () -> service.update(book));
		book.setId(1L);
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.update(book));
		book.setTitle("");
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.update(book));
		book.setTitle("new title");
		checkExeption(IllegalArgumentException.class, BOOK_NO_AUTHOR_ERROR, () -> service.update(book));
		book.getAuthors().add(new Author());
		checkExeption(IllegalArgumentException.class, BOOK_NO_GENRE_ERROR, () -> service.update(book));
	}

	@Test
	@DisplayName("проверяет параметры при получаении книги по идентификатору")
	void getByIdVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.getById(null));
	}

	@Test
	@DisplayName("проверяет параметры при получаении книги по названию")
	void getByTitleVerificationTest() {
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.getByTitle(null));
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.getByTitle(""));
	}

	@Test
	@DisplayName("проверяет параметры поиска")
	void searchVerificationTest() {
		checkExeption(IllegalArgumentException.class, TITLE_FILTER_ERROR, () -> service.search(null));
		checkExeption(IllegalArgumentException.class, TITLE_FILTER_ERROR, () -> service.search(""));
	}
}
