package ru.gurkin.spring.library.dao;

import static org.junit.jupiter.api.Assertions.*;

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

@SpringBootTest(properties = {
	       InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	       ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
	    })
@Transactional
@ActiveProfiles("test")
@DisplayName("Класс dao для авторов корректно ")
class AuthorDaoTest {

	private static final String NAME_FILTER = "test author";

	@Autowired
	AuthorDao authorDao;

	@Test
	@DisplayName("получает всех авторов")
	void getAllTest() {
		List<Author> allAuthors = authorDao.getAll();
		assertEquals(4, allAuthors.size());
	}

	@Test
	@DisplayName("создает и получает автора")
	void createAndGetTest() {
		Author newAuthor = new Author(null, "new author");
		newAuthor = authorDao.create(newAuthor);
		Author foundedAuthor = authorDao.getById(newAuthor.getId());
		assertEquals(newAuthor, foundedAuthor);
	}

	@Test
	@DisplayName("обновляет автора")
	void updateTest() {
		Author newAuthor = new Author(null, "next author");
		newAuthor = authorDao.create(newAuthor);
		newAuthor.setName("updated author");
		authorDao.update(newAuthor);
		Author updatedAuthor = authorDao.getById(newAuthor.getId());
		assertEquals(newAuthor, updatedAuthor);
	}

	@Test
	@DisplayName("ищет авторов")
	void findTest() {
		List<Author> authors = authorDao.find(NAME_FILTER);
		for (Author author : authors) {
			assertTrue(author.getName().contains(NAME_FILTER));
		}
	}

	@Test
	@DisplayName("удаляет авторов")
	void deleteTest() {
		Author newAuthor = new Author(null, "next author");
		newAuthor = authorDao.create(newAuthor);
		long id = newAuthor.getId();
		boolean isIdFound = false;
		List<Author> authors = authorDao.getAll();
		for (Author author : authors) {
			if (author.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		authorDao.delete(id);
		
		isIdFound = false;
		authors = authorDao.getAll();
		for (Author author : authors) {
			if (author.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}
}
