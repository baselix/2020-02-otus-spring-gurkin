package ru.gurkin.spring.journal.service;

import java.util.List;

import ru.gurkin.spring.journal.model.JournalUser;

public interface JournalUserService {
	List<JournalUser> getAll();

	JournalUser getByLogin(String login);

	JournalUser getById(String id);

	JournalUser create(JournalUser model);

	JournalUser update(JournalUser model);

	void delete(String id);
}
