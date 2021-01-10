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
import ru.gurkin.spring.library.repository.AuthorRepository;
import ru.gurkin.spring.library.service.impl.AuthorServiceImpl;

import static ru.gurkin.spring.library.model.ErrorConstants.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
@DisplayName("Класс сервиса для авторов корректно ")
class AuthorServiceTest {

	@MockBean
	private AuthorRepository repository;

	@InjectMocks
	private AuthorServiceImpl service;

	public <T extends Throwable> void checkExeption(Class<T> exceptionClass, String expectedMessage,
			Executable executable) {
		Throwable exception = assertThrows(exceptionClass, executable);
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("проверяет параметры поиска")
	void searchVerificationTest() {
		checkExeption(IllegalArgumentException.class, NAME_FILTER_ERROR, () -> service.search(null));
		checkExeption(IllegalArgumentException.class, NAME_FILTER_ERROR, () -> service.search(""));
	}

	@Test
	@DisplayName("проверяет параметры создаваемого автора")
	void createAuthorVerificationTest() {
		checkExeption(NullPointerException.class, AUTHOR_ERROR, () -> service.create(null));
		Author author = new Author();
		author.setId(1L);
		checkExeption(IllegalArgumentException.class, NULL_ID_ERROR, () -> service.create(author));
		author.setId(null);
		checkExeption(IllegalArgumentException.class, NAME_ERROR, () -> service.create(author));
		author.setName("");
		checkExeption(IllegalArgumentException.class, NAME_ERROR, () -> service.create(author));
	}

	@Test
	@DisplayName("проверяет параметры при удалении автора")
	void deleteAuthorVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.delete(null));
	}

	@Test
	@DisplayName("проверяет параметры обновляемого автора")
	void updateAuthorVerificationTest() {
		checkExeption(NullPointerException.class, AUTHOR_ERROR, () -> service.update(null));
		Author author = new Author();
		checkExeption(IllegalArgumentException.class, NOT_NULL_ID_ERROR, () -> service.update(author));
		author.setId(1L);
		checkExeption(IllegalArgumentException.class, NAME_ERROR, () -> service.update(author));
		author.setName("");
		checkExeption(IllegalArgumentException.class, NAME_ERROR, () -> service.update(author));
	}

	@Test
	@DisplayName("проверяет параметры при получаении автора по идентификатору")
	void getByIdVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.getById(null));
	}
}
