package ru.gurkin.spring.journal.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.JournalUser;

public interface JournalUserService {
	Flux<JournalUser> getAll();

	Mono<JournalUser> getByLogin(String login);

	Mono<JournalUser> getById(String id);

	Mono<JournalUser> create(JournalUser model);

	Mono<JournalUser> update(JournalUser model);

	void delete(String id);
}
