package ru.gurkin.spring.journal.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.gurkin.spring.journal.model.Post;
import ru.gurkin.spring.journal.repository.PostRepository;
import ru.gurkin.spring.journal.repository.UserRepository;
import ru.gurkin.spring.journal.service.impl.PostServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gurkin.spring.journal.model.ErrorConstants.*;

@SpringBootTest()
@ActiveProfiles("test")
@DirtiesContext
@DisplayName("Класс сервиса для записей корректно ")
class PostServiceTest {

	@MockBean
	private UserRepository userRepository;
	@MockBean
	private PostRepository postRepository;

	@InjectMocks
	private PostServiceImpl service;

	public <T extends Throwable> void checkExeption(Class<T> exceptionClass, String expectedMessage,
			Executable executable) {
		Throwable exception = assertThrows(exceptionClass, executable);
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("проверяет параметры создаваемой записи")
	void createBookVerificationTest() {
		checkExeption(NullPointerException.class, POST_ERROR, () -> service.create(null));
		Post post = new Post();
		post.setId("1");
		checkExeption(IllegalArgumentException.class, NULL_ID_ERROR, () -> service.create(post));
		post.setId(null);
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.create(post));
		post.setTitle("");
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.create(post));
	}

	@Test
	@DisplayName("проверяет параметры при удалении записи")
	void deleteBookVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.delete(null));
	}

	@Test
	@DisplayName("проверяет параметры обновляемой записи")
	void updateBookVerificationTest() {
		checkExeption(NullPointerException.class, POST_ERROR, () -> service.update(null));
		Post post = new Post();
		checkExeption(IllegalArgumentException.class, NOT_NULL_ID_ERROR, () -> service.update(post));
		post.setId("1");
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.update(post));
		post.setTitle("");
		checkExeption(IllegalArgumentException.class, TITLE_ERROR, () -> service.update(post));
	}

	@Test
	@DisplayName("проверяет параметры при получаении записи по идентификатору")
	void getByIdVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.getById(null));
	}

	@Test
	@DisplayName("проверяет параметры при получаении записи по названию")
	void getByTitleVerificationTest() {
		checkExeption(IllegalArgumentException.class, TITLE_FILTER_ERROR, () -> service.search(null));
		checkExeption(IllegalArgumentException.class, TITLE_FILTER_ERROR, () -> service.search(""));
	}
}
