package ru.gurkin.spring.library.service;

import java.util.List;

import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;

public interface CommentService {
	
	List<Comment> getAllComments();
	
	List<Comment> getCommentsByBook(Book book);

	Comment getById(String id);

	Comment create(Comment model);

	Comment update(Comment model);

	void delete(String id);
}
