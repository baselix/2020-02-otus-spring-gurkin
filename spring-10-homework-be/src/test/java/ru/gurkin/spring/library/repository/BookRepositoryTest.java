package ru.gurkin.spring.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Репозиторий книг корректно ")
class BookRepositoryTest {

	private static final String TITLE_FILTER = "test book";

	@Autowired
	BookRepository bookRepository;
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	GenreRepository genreRepository;

	@Test
	@DisplayName("получает все книги")
	void getAllTest() {
		List<Book> allBooks = (List<Book>) bookRepository.findAll();
		for(Book book : allBooks) {
			System.out.println(book);
		}
		assertEquals(4, allBooks.size());
	}

	@Test
	@DisplayName("получает книгу по названию")
	void geteByTitleTest() {
		Book foundedBook = bookRepository.findByTitle("test book 3");
		assertEquals("test book 3", foundedBook.getTitle());
	}

	@Test
	@DisplayName("создает и получает книгу")
	void createAndGetTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.findById(1L).orElse(null);
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.findById(1L).orElse(null);
		newBook.getGenres().add(genre);
		newBook = bookRepository.save(newBook);
		assertFalse(newBook.getAuthors().isEmpty());
		assertFalse(newBook.getGenres().isEmpty());
		Book foundedBook = bookRepository.findById(newBook.getId()).orElse(null);
		assertEquals(newBook, foundedBook);
		assertFalse(foundedBook.getAuthors().isEmpty());
		assertFalse(foundedBook.getGenres().isEmpty());
	}

	@Test
	@DisplayName("обновляет книгу")
	void updateTest() {
		Book book = bookRepository.findById(1L).orElse(null);
		Author author = new Author();
		author.setId(2L);
		author.setName("test author 2");
		book.getAuthors().add(author);
		Genre genre = new Genre(1L, "test genre 1");
		book.getGenres().remove(genre);
		String title = "new title";
		book.setTitle(title);
		Book updatedBook = bookRepository.save(book);
		assertEquals(title, updatedBook.getTitle());
		assertTrue(updatedBook.getAuthors().contains(author));
		assertFalse(updatedBook.getGenres().contains(genre));
	}

	@Test
	@DisplayName("ищет книги")
	void findTest() {
		List<Book> books = bookRepository.findByTitleLike(TITLE_FILTER);
		for (Book book : books) {
			assertTrue(book.getTitle().contains(TITLE_FILTER));
		}
	}

	@Test
	@DisplayName("удаляет книги")
	void deleteTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.findById(1L).orElse(null);
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.findById(1L).orElse(null);
		newBook.getGenres().add(genre);
		newBook = bookRepository.save(newBook);
		long id = newBook.getId();
		boolean isIdFound = false;
		List<Book> books = (List<Book>) bookRepository.findAll();
		for (Book book : books) {
			if (book.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		bookRepository.deleteById(id);

		isIdFound = false;
		books = (List<Book>) bookRepository.findAll();
		for (Book book : books) {
			if (book.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}
}
