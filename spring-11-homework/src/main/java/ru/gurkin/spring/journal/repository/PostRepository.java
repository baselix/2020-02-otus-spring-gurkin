package ru.gurkin.spring.journal.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.Post;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {
    Flux<Post> findByUser(Mono<String> userId);

    Mono<Post> findByUserAndId(Mono<String> user, String id);

    Flux<Post> findByUserAndTitleLikeIgnoreCase(Mono<String> user, String titleFilter);
}
