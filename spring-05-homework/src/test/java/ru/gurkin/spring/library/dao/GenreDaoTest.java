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

import ru.gurkin.spring.library.model.Genre;

@SpringBootTest(properties = {
	       InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	       ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
	    })
@Transactional
@ActiveProfiles("test")
@DisplayName("Класс dao для жанров корректно ")
class GenreDaoTest {

	private static final String TITLE_FILTER = "test genre";

	@Autowired
	GenreDao genreDao;

	@Test
	@DisplayName("получает все жанры")
	void getAllTest() {
		List<Genre> allAuthors = genreDao.getAll();
		assertEquals(4, allAuthors.size());
	}

	@Test
	@DisplayName("создает и получает жанр")
	void createAndGetTest() {
		Genre newGenre = new Genre(null, "new genre");
		newGenre = genreDao.create(newGenre);
		Genre foundedGenre = genreDao.getById(newGenre.getId());
		assertEquals(newGenre, foundedGenre);
	}

	@Test
	@DisplayName("обновляет жанр")
	void updateTest() {
		Genre newGenre = new Genre(null, "next genre");
		newGenre = genreDao.create(newGenre);
		newGenre.setTitle("updated genre");
		genreDao.update(newGenre);
		Genre updatedGenre = genreDao.getById(newGenre.getId());
		assertEquals(newGenre, updatedGenre);
	}

	@Test
	@DisplayName("ищет жанры")
	void findTest() {
		List<Genre> genres = genreDao.find(TITLE_FILTER);
		for (Genre author : genres) {
			assertTrue(author.getTitle().contains(TITLE_FILTER));
		}
	}

	@Test
	@DisplayName("удаляет жанры")
	void deleteTest() {
		Genre newGenre = new Genre(null, "next genre");
		newGenre = genreDao.create(newGenre);
		long id = newGenre.getId();
		boolean isIdFound = false;
		List<Genre> genres = genreDao.getAll();
		for (Genre genre : genres) {
			if (genre.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		genreDao.delete(id);
		
		isIdFound = false;
		genres = genreDao.getAll();
		for (Genre genre : genres) {
			if (genre.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}
}
