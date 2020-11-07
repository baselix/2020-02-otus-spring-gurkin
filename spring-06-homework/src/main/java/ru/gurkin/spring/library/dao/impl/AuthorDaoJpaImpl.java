package ru.gurkin.spring.library.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.dao.AuthorDao;
import ru.gurkin.spring.library.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class AuthorDaoJpaImpl implements AuthorDao {

	private static final String AUTHOR_QUERY = "select a from Author a ";
	private static final String AUTHOR_WHERE_NAME = "where a.name like :name";

	private static final String NAME_PARAM = "name";

	private static final String AUTHOR_NOT_FOUND = "Не удалось найти автора";
	
	@PersistenceContext
	private final EntityManager entityManager;

	public AuthorDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public List<Author> getAll() {
		TypedQuery<Author> query = entityManager.createQuery(AUTHOR_QUERY, Author.class);
		return query.getResultList();
	}

	@Transactional
	@Override
	public Author getById(Long id) {
		return entityManager.find(Author.class, id);
	}

	@Transactional
	@Override
	public Author create(Author author) {
		entityManager.persist(author);
		return author;
	}

	@Transactional
	@Override
	public Author update(Author author) {
		Author old = getById(author.getId());
		if (old == null) {
			throw new IllegalStateException(AUTHOR_NOT_FOUND);
		}
		if (!old.getName().equals(author.getName())) {
			return entityManager.merge(author);
		}
		return author;
	}

	@Transactional
	@Override
	public void delete(Long id) {
		Author author = getById(id);
		if(author != null) {
			entityManager.remove(author);
		}
	}

	@Transactional
	@Override
	public List<Author> find(String nameFilter) {
		TypedQuery<Author> query = entityManager.createQuery(AUTHOR_QUERY + AUTHOR_WHERE_NAME, Author.class);
		query.setParameter(NAME_PARAM, "%" + nameFilter + "%");
		return query.getResultList();
	}
}
