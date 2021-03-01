package ru.gurkin.spring.journal.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gurkin.spring.journal.model.JournalUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DisplayName("Репозиторий пользователей корректно ")
class JournalUserRepositoryTest {

//	private static final String LOGIN_FILTER = "test_user";
//
//	@Autowired
//	UserRepository userRepository;
//
//	@Autowired
//	MongoTemplate mongoTemplate;
//
//	@Test
//	@DisplayName("получает всех пользователей")
//	void getAllTest() {
//		JournalUser journalUser1 = new JournalUser("user1");
//		JournalUser journalUser2 = new JournalUser("user2");
//		userRepository.saveAll(List.of(journalUser1, journalUser2));
//		List<JournalUser> allJournalUsers = userRepository.findAll();
//		for(JournalUser journalUser : allJournalUsers) {
//			System.out.println(journalUser);
//		}
//		assertEquals(2, allJournalUsers.size());
//	}
//
//	@Test
//	@DisplayName("создает и получает пользователя")
//	void createAndGetTest() {
//		JournalUser newJournalUser = new JournalUser();
//		newJournalUser.setLogin("user1");
//		newJournalUser = userRepository.save(newJournalUser);
//		JournalUser foundedJournalUser = userRepository.findById(newJournalUser.getId()).orElse(null);
//		assertEquals(newJournalUser, foundedJournalUser);
//	}
//
//	@Test
//	@DisplayName("обновляет пользователя")
//	void updateTest() {
//		JournalUser newJournalUser = new JournalUser();
//		newJournalUser.setLogin("user1");
//		newJournalUser.setPassword("pass");
//		newJournalUser = userRepository.save(newJournalUser);
//		newJournalUser.setPassword("word");
//		userRepository.save(newJournalUser);
//		JournalUser updatedJournalUser = userRepository.findById(newJournalUser.getId()).orElse(null);
//		assertNotNull(updatedJournalUser);
//		assertEquals(newJournalUser, updatedJournalUser);
//	}
//
//	@Test
//	@DisplayName("ищет пользователя")
//	void findTest() {
//		userRepository.save(new JournalUser(LOGIN_FILTER));
//		JournalUser journalUser = userRepository.findByLoginIgnoreCase(LOGIN_FILTER);
//		assertEquals(LOGIN_FILTER, journalUser.getLogin());
//	}
//
//	@Test
//	@DisplayName("удаляет пользователя")
//	void deleteTest() {
//		JournalUser newJournalUser = userRepository.save(new JournalUser("user"));
//		String id = newJournalUser.getId();
//		boolean isIdFound = false;
//		List<JournalUser> journalUsers = userRepository.findAll();
//		for (JournalUser journalUser : journalUsers) {
//			if (journalUser.getId().equals(id)) {
//				isIdFound = true;
//				break;
//			}
//		}
//		assertTrue(isIdFound);
//		userRepository.deleteById(id);
//
//		isIdFound = false;
//		journalUsers = userRepository.findAll();
//		for (JournalUser journalUser : journalUsers) {
//			if (journalUser.getId().equals(id)) {
//				isIdFound = true;
//				break;
//			}
//		}
//		assertFalse(isIdFound);
//	}
//
//	@AfterEach
//	public void cleanDatabase(){
//		userRepository.deleteAll();
//	}
}
