package ru.gurkin.spring.library.dao;

import java.util.List;

import ru.gurkin.spring.library.model.Comment;

public interface CommentDao {
	
	Comment getById(Long id);

	Comment create(Comment model);

	Comment update(Comment model);

	void delete(Long id);

	List<Comment> getAll();
}
