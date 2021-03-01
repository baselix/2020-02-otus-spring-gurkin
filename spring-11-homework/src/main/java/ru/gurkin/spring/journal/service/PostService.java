package ru.gurkin.spring.journal.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.Post;

public interface PostService {
	Flux<Post> getAll(Mono<String> userName);

	Mono<Post> getById(String id, Mono<String> userName);

	Mono<Post> create(Post post, Mono<String> userName);

	Mono<Post> update(Post post, Mono<String> userName);

	void delete(String id);

	Flux<Post> search(String titleFilter, Mono<String> userName);
}
