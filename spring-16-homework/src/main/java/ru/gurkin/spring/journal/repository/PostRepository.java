package ru.gurkin.spring.journal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gurkin.spring.journal.model.Post;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, Long> {
    List<Post> findByTitleLikeIgnoreCase(String titleFilter);
}
