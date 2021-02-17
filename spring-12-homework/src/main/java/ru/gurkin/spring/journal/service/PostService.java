package ru.gurkin.spring.journal.service;

import java.util.List;

import ru.gurkin.spring.journal.model.Post;

public interface PostService {
	List<Post> getAll();

	List<Post> search(String titleFilter);

	Post getById(String id);

	Post create(Post model);

	Post update(Post model);

	void delete(String id);
}
