package ru.gurkin.spring.library.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DisplayName("Репозиторий книг корректно ")
class BookRepositoryTest {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	GenreRepository genreRepository;

	@Test
	@DisplayName("получает все книги")
	void getAllTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		bookRepository.save(newBook);
		List<Book> allBooks = bookRepository.findAll();
		for(Book book : allBooks) {
			System.out.println(book);
		}
		assertEquals(1, allBooks.size());
	}

	@Test
	@DisplayName("получает книгу по названию")
	void geteByTitleTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		bookRepository.save(newBook);
		Book foundedBook = bookRepository.findByTitle("new book");
		assertEquals("new book", foundedBook.getTitle());
	}

	@Test
	@DisplayName("создает и получает книгу")
	void createAndGetTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		newBook = bookRepository.save(newBook);
		assertFalse(newBook.getAuthors().isEmpty());
		assertFalse(newBook.getGenres().isEmpty());
		Book foundedBook = bookRepository.findById(newBook.getId()).orElse(null);
		assertNotNull(foundedBook);
		assertEquals(newBook, foundedBook);
		assertFalse(foundedBook.getAuthors().isEmpty());
		assertFalse(foundedBook.getGenres().isEmpty());
	}

	@Test
	@DisplayName("обновляет книгу")
	void updateTest() {
		Book book = new Book();
		book.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		book.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		book.getGenres().add(genre);
		book = bookRepository.save(book);

		Author author2 = authorRepository.save(new Author("test author 2"));
		book.getAuthors().add(author2);
		Genre genre2 = genreRepository.save(new Genre("test genre 2"));
		book.getGenres().add(genre2);
		book.getGenres().remove(genre);
		String title = "new title";
		book.setTitle(title);
		Book updatedBook = bookRepository.save(book);
		assertEquals(title, updatedBook.getTitle());
		assertTrue(updatedBook.getAuthors().contains(author2));
		assertFalse(updatedBook.getGenres().contains(genre));
	}

	@Test
	@DisplayName("ищет книги")
	void findTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		bookRepository.save(newBook);
		List<Book> books = bookRepository.findByTitleLike("new");
		for (Book book : books) {
			assertTrue(book.getTitle().contains("new"));
		}
	}

	@Test
	@DisplayName("удаляет книги")
	void deleteTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		newBook = bookRepository.save(newBook);
		String id = newBook.getId();
		boolean isIdFound = false;
		List<Book> books = bookRepository.findAll();
		for (Book book : books) {
			if (book.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		bookRepository.deleteById(id);

		isIdFound = false;
		books = bookRepository.findAll();
		for (Book book : books) {
			if (book.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}

	@AfterEach
	public void cleanDatabase(){
		bookRepository.deleteAll();
		authorRepository.deleteAll();
		genreRepository.deleteAll();
	}
}
