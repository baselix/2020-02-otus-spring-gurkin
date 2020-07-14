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
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.impl.BookDaoJpaImpl;
import ru.gurkin.spring.library.dao.impl.CommentDaoJpaImpl;
import ru.gurkin.spring.library.model.Comment;

@ActiveProfiles("test")
@DataJpaTest
@Transactional
@Import({CommentDaoJpaImpl.class, BookDaoJpaImpl.class})
@DisplayName("Класс dao для комментариев корректно ")
class CommentDaoTest {

	@Autowired
	CommentDaoJpaImpl commentDao;
	@Autowired
	BookDaoJpaImpl bookDao;
	

	@Test
	@DisplayName("получает все комментарии")
	void getAllTest() {
		List<Comment> allComments = commentDao.getAll();
		assertEquals(1, allComments.size());
	}
	
	@Test
	@DisplayName("создает и получает комментарий")
	void createAndGetTest() {
		Comment newComment = new Comment();
		newComment.setBookId(1L);
		newComment.setMessage("next genre");
		newComment = commentDao.create(newComment);
		Comment foundedComment = commentDao.getById(newComment.getId());
		assertEquals(newComment, foundedComment);
	}

	@Test
	@DisplayName("обновляет комментарий")
	void updateTest() {
		Comment newComment = new Comment();
		newComment.setBookId(1L);
		newComment.setMessage("next genre");
		newComment = commentDao.create(newComment);
		newComment.setMessage("updated comment");
		commentDao.update(newComment);
		Comment updatedComment = commentDao.getById(newComment.getId());
		assertEquals(newComment, updatedComment);
	}

	@Test
	@DisplayName("ищет комментарии по bookId")
	void findTest() {
		List<Comment> comments = commentDao.getCommentsByBookId(1l);
		for (Comment comment : comments) {
			assertTrue(comment.getBookId().equals(1L));
		}
	}

	@Test
	@DisplayName("удаляет комментарий")
	void deleteTest() {
		Comment newComment = new Comment();
		newComment.setBookId(1L);
		newComment.setMessage("next genre");
		newComment = commentDao.create(newComment);
		long id = newComment.getId();
		boolean isIdFound = false;
		List<Comment> comments = commentDao.getAll();
		for (Comment comment : comments) {
			if (comment.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		commentDao.delete(id);
		
		isIdFound = false;
		comments = commentDao.getAll();
		for (Comment comment : comments) {
			if (comment.getId() == id) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}
}
