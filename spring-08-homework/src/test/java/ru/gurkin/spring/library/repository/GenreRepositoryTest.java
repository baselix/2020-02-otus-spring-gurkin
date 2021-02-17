package ru.gurkin.spring.library.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gurkin.spring.library.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DisplayName("Репозиторий жанров корректно ")
class GenreRepositoryTest {

	@Autowired
	GenreRepository genreRepository;

	@Test
	@DisplayName("получает все жанры")
	void getAllTest() {
		genreRepository.save(new Genre("genre"));
		List<Genre> allGenres = genreRepository.findAll();
		assertEquals(1, allGenres.size());
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
		Genre newGenre = new Genre("next genre");
		newGenre = genreRepository.save(newGenre);
		newGenre.setTitle("updated genre");
		genreRepository.save(newGenre);
		Genre updatedGenre = genreRepository.findById(newGenre.getId()).orElse(null);
		assertNotNull(updatedGenre);
		assertEquals(newGenre, updatedGenre);
	}

	@Test
	@DisplayName("ищет жанры")
	void findTest() {
		genreRepository.save(new Genre("new genre"));
		List<Genre> genres = genreRepository.findByTitle("new genre");
		for (Genre author : genres) {
			assertTrue(author.getTitle().contains("new genre"));
		}
	}

	@Test
	@DisplayName("удаляет жанры")
	void deleteTest() {
		Genre newGenre = genreRepository.save(new Genre("next genre"));
		String id = newGenre.getId();
		boolean isIdFound = false;
		List<Genre> genres = genreRepository.findAll();
		for (Genre genre : genres) {
			if (genre.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		genreRepository.deleteById(id);

		isIdFound = false;
		genres = genreRepository.findAll();
		for (Genre genre : genres) {
			if (genre.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}

	@AfterEach
	public void clear(){
		genreRepository.deleteAll();
	}
}
