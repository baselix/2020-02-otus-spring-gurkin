package ru.gurkin.spring.journal.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.repository.PostRepository;
import ru.gurkin.spring.journal.repository.UserRepository;
import ru.gurkin.spring.journal.service.impl.JournalUserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gurkin.spring.journal.model.ErrorConstants.*;

@SpringBootTest()
@ActiveProfiles("test")
@DirtiesContext
@DisplayName("Класс сервиса для пользователей корректно ")
class UserServiceTest {

	@MockBean
	private UserRepository userRepository;
//	@MockBean
//	private PostRepository postRepository;

	@InjectMocks
	private JournalUserServiceImpl service;

	public <T extends Throwable> void checkExeption(Class<T> exceptionClass, String expectedMessage,
			Executable executable) {
		Throwable exception = assertThrows(exceptionClass, executable);
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("проверяет параметры поиска")
	void searchVerificationTest() {
		checkExeption(IllegalArgumentException.class, LOGIN_ERROR, () -> service.getByLogin(null));
		checkExeption(IllegalArgumentException.class, LOGIN_ERROR, () -> service.getByLogin(""));
	}

	@Test
	@DisplayName("проверяет параметры создаваемого пользователя")
	void createUserVerificationTest() {
		checkExeption(NullPointerException.class, USER_ERROR, () -> service.create(null));
		JournalUser journalUser = new JournalUser();
		journalUser.setId(1L);
		checkExeption(IllegalArgumentException.class, NULL_ID_ERROR, () -> service.create(journalUser));
		journalUser.setId(null);
		checkExeption(IllegalArgumentException.class, LOGIN_ERROR, () -> service.create(journalUser));
		journalUser.setLogin("");
		checkExeption(IllegalArgumentException.class, LOGIN_ERROR, () -> service.create(journalUser));
	}

	@Test
	@DisplayName("проверяет параметры при удалении пользователя")
	void deleteUserVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.delete(null));
	}

	@Test
	@DisplayName("проверяет параметры обновляемого пользователя")
	void updateUserVerificationTest() {
		checkExeption(NullPointerException.class, USER_ERROR, () -> service.update(null));
		JournalUser journalUser = new JournalUser();
		checkExeption(IllegalArgumentException.class, NOT_NULL_ID_ERROR, () -> service.update(journalUser));
		journalUser.setId(1L);
		checkExeption(IllegalArgumentException.class, LOGIN_ERROR, () -> service.update(journalUser));
		journalUser.setLogin("");
		checkExeption(IllegalArgumentException.class, LOGIN_ERROR, () -> service.update(journalUser));
	}

	@Test
	@DisplayName("проверяет параметры при получаении пользователя по идентификатору")
	void getByIdVerificationTest() {
		checkExeption(NullPointerException.class, ID_ERROR, () -> service.getById(null));
	}
}
