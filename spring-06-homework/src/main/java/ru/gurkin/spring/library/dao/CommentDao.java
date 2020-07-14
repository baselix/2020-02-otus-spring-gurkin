package ru.gurkin.spring.library.dao;

import java.util.List;

import ru.gurkin.spring.library.model.Comment;

public interface CommentDao {
	
	List<Comment> getCommentsByBookId(Long id);

	Comment getById(Long id);

	Comment create(Comment model);

	Comment update(Comment model);

	void delete(Long id);

	List<Comment> getAll();
}
