package ru.gurkin.spring.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Репозиторий комментариев корректно ")
class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	BookRepository bookRepository;


	@Test
	@DisplayName("получает все комментарии")
	void getAllTest() {
		List<Comment> allComments = (List<Comment>) commentRepository.findAll();
		assertEquals(1, allComments.size());
	}

	@Test
	@DisplayName("создает и получает комментарий")
	void createAndGetTest() {
		Book book = bookRepository.findById(1L).orElse(null);
		Comment newComment = new Comment();
		newComment.setBook(book);
		newComment.setMessage("next genre");
		newComment = commentRepository.save(newComment);
		Comment foundedComment = commentRepository.findById(newComment.getId()).orElse(null);
		assertEquals(newComment, foundedComment);
	}

	@Test
	@DisplayName("обновляет комментарий")
	void updateTest() {
		Book book = bookRepository.findById(1L).orElse(null);
		Comment newComment = new Comment();
		newComment.setBook(book);
		newComment.setMessage("next genre");
		newComment = commentRepository.save(newComment);
		newComment.setMessage("updated comment");
		commentRepository.save(newComment);
		Comment updatedComment = commentRepository.findById(newComment.getId()).orElse(null);
		assertEquals(newComment, updatedComment);
	}

	@Test
	@DisplayName("ищет комментарии по bookId")
	void findTest() {
	    Book book = bookRepository.findById(1L).orElse(null);
		List<Comment> comments = commentRepository.findByBook(book);
		for (Comment comment : comments) {
			assertEquals(1L, comment.getBook().getId());
		}
	}

	@Test
	@DisplayName("удаляет комментарий")
	void deleteTest() {
		Book book = bookRepository.findById(1L).orElse(null);
		Comment newComment = new Comment();
		newComment.setBook(book);
		newComment.setMessage("next genre");
		newComment = commentRepository.save(newComment);
		long id = newComment.getId();
		boolean isIdFound = false;
		List<Comment> comments = (List<Comment>) commentRepository.findAll();
		for (Comment comment : comments) {
			if (comment.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		commentRepository.deleteById(id);

		isIdFound = false;
		comments = (List<Comment>) commentRepository.findAll();
		for (Comment comment : comments) {
			if (comment.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}
}
