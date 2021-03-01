package ru.gurkin.spring.library.dao.impl;

import org.springframework.stereotype.Repository;
import ru.gurkin.spring.library.dao.BookDao;
import ru.gurkin.spring.library.model.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class BookDaoJpaImpl implements BookDao {

	private static final String FETCH_GRAPH_PROPERTY = "javax.persistence.fetchgraph";
	private static final String BOOK_GRAPH_NAME = "graph.BookGraph";

	private static final String SIMPLE_JPQL_BOOK_QUERY = "select b from Book b ";
	private static final String BOOK_WHERE_TITLE = "where b.title = :title";
	private static final String BOOK_WHERE_TITLE_LIKE = "where b.title like :title";

	private static final String TITLE_PARAM = "title";

	@PersistenceContext
	private final EntityManager entityManager;

	public BookDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Book> getAll() {
		EntityGraph<?> entityGraph = entityManager.createEntityGraph(BOOK_GRAPH_NAME);
		TypedQuery<Book> query = entityManager.createQuery(SIMPLE_JPQL_BOOK_QUERY, Book.class)
				.setHint(FETCH_GRAPH_PROPERTY, entityGraph);
		return query.getResultList();
	}

	@Override
	public Book getById(Long id) {
		return entityManager.find(Book.class, id);
	}

	@Override
	public Book create(Book book) {
		entityManager.persist(book);
		return book;
	}

	@Override
	public Book update(Book book) {
		return entityManager.merge(book);
	}

	@Override
	public void delete(Long id) {
		Book book = getById(id);
		if(book != null) {
			entityManager.remove(book);
		}
	}

	@Override
	public Book getByTitle(String title) {
		EntityGraph<?> entityGraph = entityManager.createEntityGraph(BOOK_GRAPH_NAME);
		TypedQuery<Book> query = entityManager.createQuery(SIMPLE_JPQL_BOOK_QUERY + BOOK_WHERE_TITLE, Book.class)
				.setHint(FETCH_GRAPH_PROPERTY, entityGraph);
		query.setParameter(TITLE_PARAM, title);
		return query.getSingleResult();
	}

	@Override
	public List<Book> search(String titleFilter) {
		EntityGraph<?> entityGraph = entityManager.createEntityGraph(BOOK_GRAPH_NAME);
		TypedQuery<Book> query = entityManager.createQuery(SIMPLE_JPQL_BOOK_QUERY + BOOK_WHERE_TITLE_LIKE, Book.class)
				.setHint(FETCH_GRAPH_PROPERTY, entityGraph);
		query.setParameter(TITLE_PARAM, titleFilter);
		return query.getResultList();
	}
}
