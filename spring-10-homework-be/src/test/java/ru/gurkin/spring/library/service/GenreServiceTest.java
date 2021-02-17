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

import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.repository.GenreRepository;
import ru.gurkin.spring.library.service.impl.GenreServiceImpl;

import static ru.gurkin.spring.library.model.ErrorConstants.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
@DisplayName("Класс сервиса для жанров корректно ")
class GenreServiceTest {

	@MockBean
	private GenreRepository repository;

	@InjectMocks
	private GenreServiceImpl service;

	public <T extends Throwable> void checkExeption(Class<T> exceptionClass, String expectedMessage,
			Executable executable) {
		Throwable exception = assertThrows(exceptionClass, executable);
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("проверяет параметры поиска")
	void searchVerificationTest() {
		checkExeption(IllegalArgumentException.class, TITLE_FILTER_ERROR, () -> service.search(null));
		checkExeption(IllegalArgumentException.class, TITLE_FILTER_ERROR, () -> service.search(""));
	}

	@Test
	@DisplayName("проверяет параметры создаваемого жанра")
	void createGenreVerificationTest() {
		checkExeption(NullPointerException.class, GENRE_ERROR, () -> service.create(null));
		Genre genre = new Genre(1L, null);
		checkExeption(IllegalArgumentException.class, NULL_ID_ERROR, () -> service.create(genre));
		genre.setId(null);
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.create(genre));
		genre.setTitle("");
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.create(genre));
	}

	@Test
	@DisplayName("проверяет параметры при удалении жанра")
	void deleteGenreVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.delete(null));
	}

	@Test
	@DisplayName("проверяет параметры обновляемого жанра")
	void updateGenreVerificationTest() {
		checkExeption(NullPointerException.class, GENRE_ERROR, () -> service.update(null));
		Genre genre = new Genre(null, null);
		checkExeption(IllegalArgumentException.class, NOT_NULL_ID_ERROR, () -> service.update(genre));
		genre.setId(1L);
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.update(genre));
		genre.setTitle("");
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.update(genre));
	}

	@Test
	@DisplayName("проверяет параметры при получаении жанра по идентификатору")
	void getByIdVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.getById(null));
	}

}
