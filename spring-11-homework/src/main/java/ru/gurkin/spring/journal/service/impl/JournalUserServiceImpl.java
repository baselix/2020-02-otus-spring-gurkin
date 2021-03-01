package ru.gurkin.spring.journal.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.repository.UserRepository;
import ru.gurkin.spring.journal.service.JournalUserService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.journal.model.ErrorConstants.*;

@Service
public class JournalUserServiceImpl implements JournalUserService {

	private final UserRepository dao;
	
	public JournalUserServiceImpl(UserRepository dao) {
		this.dao = dao;
	}

	@Override
	public Flux<JournalUser> getAll() {
		return dao.findAll();
	}

	@Override
	public Mono<JournalUser> getByLogin(String login) {
		checkArgument(!Strings.isNullOrEmpty(login), LOGIN_ERROR);
		return dao.findByLoginIgnoreCase(login);
	}

	@Override
	public Mono<JournalUser> getById(String id) {
		checkNotNull(id, ID_ERROR);
		return dao.findById(id);
	}

	@Override
	public Mono<JournalUser> create(JournalUser journalUser) {
		checkNotNull(journalUser, USER_ERROR);
		checkArgument(journalUser.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(journalUser.getLogin()), LOGIN_ERROR);
		return dao.save(journalUser);
	}

	@Override
	public Mono<JournalUser> update(JournalUser journalUser) {
		checkNotNull(journalUser, USER_ERROR);
		checkArgument(journalUser.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(journalUser.getLogin()), LOGIN_ERROR);
		return dao.save(journalUser);
	}

	@Override
	public void delete(String id) {
		checkNotNull(id, ID_ERROR);
		dao.deleteById(id);
	}

}
