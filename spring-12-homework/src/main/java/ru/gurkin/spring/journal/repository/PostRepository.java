package ru.gurkin.spring.journal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.gurkin.spring.journal.model.Post;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByUser(String userId);
    List<Post> findByUserAndTitleLikeIgnoreCase(String userId, String titleFilter);
}
