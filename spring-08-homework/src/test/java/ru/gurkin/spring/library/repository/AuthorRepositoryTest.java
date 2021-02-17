package ru.gurkin.spring.library.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.repository.AuthorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DisplayName("Репозиторий авторов корректно ")
class AuthorRepositoryTest {

	private static final String NAME_FILTER = "test author";

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Test
	@DisplayName("получает всех авторов")
	void getAllTest() {
		Author author1 = new Author("author1");
		Author author2 = new Author("author2");
		authorRepository.saveAll(List.of(author1, author2));
		List<Author> allAuthors = authorRepository.findAll();
		for(Author author : allAuthors) {
			System.out.println(author);
		}
		assertEquals(2, allAuthors.size());
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
		Author newAuthor = authorRepository.save(new Author("next author"));
		String id = newAuthor.getId();
		boolean isIdFound = false;
		List<Author> authors = authorRepository.findAll();
		for (Author author : authors) {
			if (author.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		authorRepository.deleteById(id);
		
		isIdFound = false;
		authors = authorRepository.findAll();
		for (Author author : authors) {
			if (author.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}

	@AfterEach
	public void cleanDatabase(){
		authorRepository.deleteAll();
	}
}
