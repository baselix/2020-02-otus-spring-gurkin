package ru.gurkin.spring.library.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.GenreDao;
import ru.gurkin.spring.library.model.Genre;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
@Repository
public class GenreDaoJpaImpl implements GenreDao {

	private static final String GENRE_QUERY = "select g from Genre g ";
	private static final String GENRE_WHERE_TITLE = "where g.title LIKE :title";
	private static final String DELETE_GENRE_QUERY = "delete from Genre g where g.id = :id";
	
	private static final String ID_PARAM = "id";
	private static final String TITLE_PARAM = "title";

	private static final String GENRE_NOT_FOUND = "Не удалось найти жанр";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Genre> getAll() {
		TypedQuery<Genre> query = entityManager.createQuery(GENRE_QUERY, Genre.class);
		return query.getResultList();
	}

	@Override
	public Genre getById(Long id) {
		return entityManager.find(Genre.class, id);
	}

	@Override
	public Genre create(Genre genre) {
		entityManager.persist(genre);
		return genre;
	}

	@Override
	public Genre update(Genre genre) {
		Genre old = getById(genre.getId());
		if (old == null) {
			throw new IllegalStateException(GENRE_NOT_FOUND);
		}
		if (!old.getTitle().equals(genre.getTitle())) {
			return entityManager.merge(genre);
		}
		return genre;
	}

	@Override
	public void delete(Long id) {
		Query query = entityManager.createQuery(DELETE_GENRE_QUERY);
		query.setParameter(ID_PARAM, id);
		query.executeUpdate();
	}

	@Override
	public List<Genre> find(String titleFilter) {
		TypedQuery<Genre> query = entityManager.createQuery(GENRE_QUERY + GENRE_WHERE_TITLE, Genre.class);
		query.setParameter(TITLE_PARAM, "%" + titleFilter + "%");
		return query.getResultList();
	}
}
