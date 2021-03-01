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

import ru.gurkin.spring.library.dao.impl.GenreDaoJpaImpl;
import ru.gurkin.spring.library.model.Genre;

@ActiveProfiles("test")
@DataJpaTest
@Import(GenreDaoJpaImpl.class)
@DisplayName("Класс dao для жанров корректно ")
class GenreDaoTest {

	private static final String TITLE_FILTER = "test genre";

	@Autowired
	GenreDaoJpaImpl genreDao;

	@Test
	@DisplayName("получает все жанры")
	void getAllTest() {
		List<Genre> allGenres = genreDao.getAll();
		assertEquals(4, allGenres.size());
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
		for (Genre genre : genres) {
			assertTrue(genre.getTitle().contains(TITLE_FILTER));
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
