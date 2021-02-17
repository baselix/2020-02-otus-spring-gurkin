package ru.gurkin.spring.library.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

import java.util.List;

import org.springframework.stereotype.Service;
import com.google.common.base.Strings;

import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.repository.AuthorRepository;
import ru.gurkin.spring.library.service.AuthorService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorServiceImpl implements AuthorService{

	private final AuthorRepository dao;
	
	public AuthorServiceImpl(AuthorRepository dao) {
		this.dao = dao;
	}

	@Override
	public List<Author> getAll() {
		return (List<Author>) dao.findAll();
	}

	@Override
	public List<Author> search(String nameFilter) {
		checkArgument(!Strings.isNullOrEmpty(nameFilter), NAME_FILTER_ERROR);
		return dao.findByNameLikeIgnoreCase(nameFilter);
	}

	@Override
	public Author getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.findById(id).
				orElse(null);
	}

	@Transactional
	@Override
	public Author create(Author author) {
		checkNotNull(author, AUTHOR_ERROR);
		checkArgument(author.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(author.getName()), NAME_ERROR);
		return dao.save(author);
	}

	@Transactional
	@Override
	public Author update(Author author) {
		checkNotNull(author, AUTHOR_ERROR);
		checkArgument(author.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(author.getName()), NAME_ERROR);
		return dao.save(author);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		dao.deleteById(id);
	}

}
