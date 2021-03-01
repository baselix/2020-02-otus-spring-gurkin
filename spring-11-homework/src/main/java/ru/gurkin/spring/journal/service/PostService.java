package ru.gurkin.spring.journal.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.Post;

public interface PostService {
	Flux<Post> getAll();

	Mono<Post> getById(String id);

	Mono<Post> create(Post post);

	Mono<Post> update(Post post);

	void delete(String id);

	Flux<Post> search(String titleFilter);
}
