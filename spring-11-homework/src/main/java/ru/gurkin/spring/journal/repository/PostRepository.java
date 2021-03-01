package ru.gurkin.spring.journal.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.gurkin.spring.journal.model.Post;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {

    Flux<Post> findByTitleLikeIgnoreCase(String titleFilter);
}
