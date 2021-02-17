package ru.gurkin.spring.journal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.gurkin.spring.journal.model.JournalUser;

@Repository
public interface UserRepository extends MongoRepository<JournalUser, String> {
	JournalUser findByLoginIgnoreCase(String login);
}
