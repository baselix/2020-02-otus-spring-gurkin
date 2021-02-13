package ru.gurkin.spring.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gurkin.spring.library.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Репозиторий авторов корректно ")
class AuthorRepositoryTest {

	private static final String NAME_FILTER = "test author";

	@Autowired
	AuthorRepository authorRepository;

	@Test
	@DisplayName("получает всех авторов")
	void getAllTest() {
		List<Author> allAuthors = (List<Author>) authorRepository.findAll();
		for(Author author : allAuthors) {
			System.out.println(author);
		}
		assertEquals(4, allAuthors.size());
	}

	@Test
	@DisplayName("создает и получает автора")
	void createAndGetTest() {
		Author newAuthor = new Author();
		newAuthor.setName("new author");
		newAuthor = authorRepository.save(newAuthor);
		Author foundedAuthor = authorRepository.findById(newAuthor.getId()).orElse(null);
		assertEquals(newAuthor, foundedAuthor);
	}

	@Test
	@DisplayName("обновляет автора")
	void updateTest() {
		Author newAuthor = new Author();
		newAuthor.setName("next author");
		newAuthor = authorRepository.save(newAuthor);
		newAuthor.setName("updated author");
		authorRepository.save(newAuthor);
		Author updatedAuthor = authorRepository.findById(newAuthor.getId()).orElse(null);
		assertEquals(newAuthor, updatedAuthor);
	}

	@Test
	@DisplayName("ищет авторов")
	void findTest() {
		List<Author> authors = authorRepository.findByNameLikeIgnoreCase(NAME_FILTER);
		for (Author author : authors) {
			assertTrue(author.getName().contains(NAME_FILTER));
		}
	}

	@Test
	@DisplayName("удаляет авторов")
	void deleteTest() {
		Author newAuthor = new Author();
		newAuthor.setName("next author");
		newAuthor = authorRepository.save(newAuthor);
		long id = newAuthor.getId();
		boolean isIdFound = false;
		List<Author> authors = (List<Author>) authorRepository.findAll();
		for (Author author : authors) {
			if (author.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		authorRepository.deleteById(id);
		
		isIdFound = false;
		authors = (List<Author>) authorRepository.findAll();
		for (Author author : authors) {
			if (author.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}
}
