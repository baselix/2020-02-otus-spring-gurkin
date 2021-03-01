package ru.gurkin.spring.journal.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.JournalUser;

@Repository
public interface UserRepository extends ReactiveMongoRepository<JournalUser, String> {
	Mono<JournalUser> findByLoginIgnoreCase(String login);
}
