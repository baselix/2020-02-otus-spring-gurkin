package ru.gurkin.spring.library.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.AuthorDao;
import ru.gurkin.spring.library.model.Author;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
@Repository
public class AuthorDaoJpaImpl implements AuthorDao {

	private static final String AUTHOR_QUERY = "select a from Author a ";
	private static final String AUTHOR_WHERE_NAME = "where a.name like :name";
	private static final String DELETE_AUTHOR_QUERY = "delete from Author a where a.id = :id";

	private static final String ID_PARAM = "id";
	private static final String NAME_PARAM = "name";

	private static final String AUTHOR_NOT_FOUND = "Не удалось найти автора";
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Author> getAll() {
		TypedQuery<Author> query = entityManager.createQuery(AUTHOR_QUERY, Author.class);
		return query.getResultList();
	}

	@Override
	public Author getById(Long id) {
		return entityManager.find(Author.class, id);
	}

	@Override
	public Author create(Author author) {
		entityManager.persist(author);
		return author;
	}

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

	@Override
	public void delete(Long id) {
		Query query = entityManager.createQuery(DELETE_AUTHOR_QUERY);
		query.setParameter(ID_PARAM, id);
		query.executeUpdate();
	}

	@Override
	public List<Author> find(String nameFilter) {
		TypedQuery<Author> query = entityManager.createQuery(AUTHOR_QUERY + AUTHOR_WHERE_NAME, Author.class);
		query.setParameter(NAME_PARAM, "%" + nameFilter + "%");
		return query.getResultList();
	}
}
