package ru.gurkin.spring.library.dao.impl;

import org.springframework.stereotype.Repository;
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

	@PersistenceContext
	private final EntityManager entityManager;

	public GenreDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

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
		return entityManager.merge(genre);
	}

	@Override
	public void delete(Long id) {
		Genre genre = getById(id);
		if(genre != null) {
			entityManager.remove(genre);
		}
	}

	@Override
	public List<Genre> find(String titleFilter) {
		TypedQuery<Genre> query = entityManager.createQuery(GENRE_QUERY + GENRE_WHERE_TITLE, Genre.class);
		query.setParameter(TITLE_PARAM, "%" + titleFilter + "%");
		return query.getResultList();
	}
}
