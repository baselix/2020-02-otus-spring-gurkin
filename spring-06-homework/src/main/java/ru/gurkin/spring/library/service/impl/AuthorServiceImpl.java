package ru.gurkin.spring.library.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import ru.gurkin.spring.library.dao.AuthorDao;
import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.service.AuthorService;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Service
public class AuthorServiceImpl implements AuthorService{

	private final AuthorDao dao;
	
	public AuthorServiceImpl(AuthorDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Author> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Author> search(String nameFilter) {
		checkArgument(!Strings.isNullOrEmpty(nameFilter), NAME_FILTER_ERROR);
		return dao.find(nameFilter);
	}

	@Override
	public Author getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.getById(id);
	}

	@Override
	public Author create(Author author) {
		checkNotNull(author, AUTHOR_ERROR);
		checkArgument(author.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(author.getName()), NAME_ERROR);
		return dao.create(author);
	}

	@Override
	public Author update(Author author) {
		checkNotNull(author, AUTHOR_ERROR);
		checkArgument(author.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(author.getName()), NAME_ERROR);
		return dao.update(author);
	}

	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		dao.delete(id);
	}

}
