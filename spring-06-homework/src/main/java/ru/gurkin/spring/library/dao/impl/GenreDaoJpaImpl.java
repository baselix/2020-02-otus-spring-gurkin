package ru.gurkin.spring.library.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.dao.GenreDao;
import ru.gurkin.spring.library.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenreDaoJpaImpl implements GenreDao {

	private static final String GENRE_QUERY = "select g from Genre g ";
	private static final String GENRE_WHERE_TITLE = "where g.title LIKE :title";
	
	private static final String TITLE_PARAM = "title";

	private static final String GENRE_NOT_FOUND = "Не удалось найти жанр";

	@PersistenceContext
	private final EntityManager entityManager;

	public GenreDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public List<Genre> getAll() {
		TypedQuery<Genre> query = entityManager.createQuery(GENRE_QUERY, Genre.class);
		return query.getResultList();
	}

	@Transactional
	@Override
	public Genre getById(Long id) {
		return entityManager.find(Genre.class, id);
	}

	@Transactional
	@Override
	public Genre create(Genre genre) {
		entityManager.persist(genre);
		return genre;
	}

	@Transactional
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

	@Transactional
	@Override
	public void delete(Long id) {
		Genre genre = getById(id);
		if(genre != null) {
			entityManager.remove(genre);
		}
	}

	@Transactional
	@Override
	public List<Genre> find(String titleFilter) {
		TypedQuery<Genre> query = entityManager.createQuery(GENRE_QUERY + GENRE_WHERE_TITLE, Genre.class);
		query.setParameter(TITLE_PARAM, "%" + titleFilter + "%");
		return query.getResultList();
	}
}
