package ru.gurkin.spring.journal.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.repository.UserRepository;
import ru.gurkin.spring.journal.service.JournalUserService;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.journal.model.ErrorConstants.*;

@Service
public class JournalUserServiceImpl implements JournalUserService {

	private final UserRepository dao;
	private final SequenceGeneratorService sequenceGenerator;

	public JournalUserServiceImpl(UserRepository dao, SequenceGeneratorService sequenceGenerator) {
		this.dao = dao;
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public List<JournalUser> getAll() {
		return dao.findAll();
	}

	@Override
	public JournalUser getByLogin(String login) {
		checkArgument(!Strings.isNullOrEmpty(login), LOGIN_ERROR);
		return dao.findByLoginIgnoreCase(login);
	}

	@Override
	public JournalUser getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.findById(id).
				orElse(null);
	}

	@Override
	public JournalUser create(JournalUser journalUser) {
		checkNotNull(journalUser, USER_ERROR);
		checkArgument(journalUser.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(journalUser.getLogin()), LOGIN_ERROR);
		journalUser.setId(sequenceGenerator.generateSequence(JournalUser.SEQUENCE_NAME));
		return dao.save(journalUser);
	}

	@Override
	public JournalUser update(JournalUser journalUser) {
		checkNotNull(journalUser, USER_ERROR);
		checkArgument(journalUser.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(journalUser.getLogin()), LOGIN_ERROR);
		return dao.save(journalUser);
	}

	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		dao.deleteById(id);
	}

}
