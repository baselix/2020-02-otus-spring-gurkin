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
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DisplayName("Репозиторий комментариев корректно ")
class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	GenreRepository genreRepository;


	@Test
	@DisplayName("получает все комментарии")
	void getAllTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		bookRepository.save(newBook);
		commentRepository.save(new Comment(null, newBook, "comment"));
		List<Comment> allComments = commentRepository.findAll();
		assertEquals(1, allComments.size());
	}

	@Test
	@DisplayName("создает и получает комментарий")
	void createAndGetTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		bookRepository.save(newBook);
		Comment newComment = commentRepository.save(new Comment(null, newBook, "comment"));
		Comment foundedComment = commentRepository.findById(newComment.getId()).orElse(null);
		assertEquals(newComment, foundedComment);
	}

	@Test
	@DisplayName("обновляет комментарий")
	void updateTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		bookRepository.save(newBook);
		Comment newComment = commentRepository.save(new Comment(null, newBook, "comment"));
		newComment.setMessage("updated comment");
		commentRepository.save(newComment);
		Comment updatedComment = commentRepository.findById(newComment.getId()).orElse(null);
		assertNotNull(updatedComment);
		assertEquals(newComment, updatedComment);
	}

	@Test
	@DisplayName("ищет комментарии по bookId")
	void findTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		bookRepository.save(newBook);
		commentRepository.save(new Comment(null, newBook, "comment"));
		List<Comment> comments = commentRepository.findByBook(newBook);
		for (Comment comment : comments) {
			assertEquals(newBook.getId(), comment.getBook().getId());
		}
	}

	@Test
	@DisplayName("удаляет комментарий")
	void deleteTest() {
		Book newBook = new Book();
		newBook.setTitle("new book");
		Author author = authorRepository.save(new Author("author1"));
		newBook.getAuthors().add(author);
		Genre genre = genreRepository.save(new Genre("genre1"));
		newBook.getGenres().add(genre);
		bookRepository.save(newBook);
		Comment newComment = commentRepository.save(new Comment(null, newBook, "comment"));
		String id = newComment.getId();
		boolean isIdFound = false;
		List<Comment> comments = commentRepository.findAll();
		for (Comment comment : comments) {
			if (comment.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		commentRepository.deleteById(id);

		isIdFound = false;
		comments = commentRepository.findAll();
		for (Comment comment : comments) {
			if (comment.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}

	@AfterEach
	public void clear(){
		commentRepository.deleteAll();
		bookRepository.deleteAll();
		genreRepository.deleteAll();
		authorRepository.deleteAll();
	}
}
