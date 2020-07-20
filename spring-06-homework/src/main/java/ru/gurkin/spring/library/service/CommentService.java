package ru.gurkin.spring.library.service;

import java.util.List;

import ru.gurkin.spring.library.model.Comment;

public interface CommentService {
	
	List<Comment> getAllComments();
	
	List<Comment> getCommentsByBookId(Long bookId);

	Comment getById(Long id);

	Comment create(Comment model);

	Comment update(Comment model);

	void delete(Long id);
}
