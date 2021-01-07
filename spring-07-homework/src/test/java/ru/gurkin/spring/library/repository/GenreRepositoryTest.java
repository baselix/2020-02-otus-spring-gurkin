package ru.gurkin.spring.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gurkin.spring.library.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Репозиторий жанров корректно ")
class GenreRepositoryTest {

	private static final String TITLE_FILTER = "test genre";

	@Autowired
	GenreRepository genreRepository;

	@Test
	@DisplayName("получает все жанры")
	void getAllTest() {
		List<Genre> allGenres = (List<Genre>) genreRepository.findAll();
		assertEquals(4, allGenres.size());
	}

	@Test
	@DisplayName("создает и получает жанр")
	void createAndGetTest() {
		Genre newGenre = new Genre(null, "new genre");
		newGenre = genreRepository.save(newGenre);
		Genre foundedGenre = genreRepository.findById(newGenre.getId()).orElse(null);
		assertEquals(newGenre, foundedGenre);
	}

	@Test
	@DisplayName("обновляет жанр")
	void updateTest() {
		Genre newGenre = new Genre(null, "next genre");
		newGenre = genreRepository.save(newGenre);
		newGenre.setTitle("updated genre");
		genreRepository.save(newGenre);
		Genre updatedGenre = genreRepository.findById(newGenre.getId()).orElse(null);
		assertEquals(newGenre, updatedGenre);
	}

	@Test
	@DisplayName("ищет жанры")
	void findTest() {
		List<Genre> genres = genreRepository.findByTitle(TITLE_FILTER);
		for (Genre author : genres) {
			assertTrue(author.getTitle().contains(TITLE_FILTER));
		}
	}

	@Test
	@DisplayName("удаляет жанры")
	void deleteTest() {
		Genre newGenre = new Genre(null, "next genre");
		newGenre = genreRepository.save(newGenre);
		long id = newGenre.getId();
		boolean isIdFound = false;
		List<Genre> genres = (List<Genre>) genreRepository.findAll();
		for (Genre genre : genres) {
			if (genre.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		genreRepository.deleteById(id);

		isIdFound = false;
		genres = (List<Genre>) genreRepository.findAll();
		for (Genre genre : genres) {
			if (genre.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}
}
