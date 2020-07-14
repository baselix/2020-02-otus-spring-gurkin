package ru.gurkin.spring.library.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.BookDao;
import ru.gurkin.spring.library.model.Book;

@Transactional(propagation=Propagation.REQUIRED, readOnly=false, noRollbackFor=Exception.class)
@Repository
public class BookDaoJpaImpl implements BookDao {

	private static final String SIMPLE_BOOK_QUERY = "select distinct b from Book b join fetch b.authors a join fetch b.genres g ";
	private static final String BOOK_WHERE_TITLE = "where b.title = :title";
	private static final String BOOK_WHERE_TITLE_LIKE = "where b.title like :title";
	private static final String DELETE_BOOK_QUERY = "delete from Book b where b.id = :id";

	private static final String BOOK_NOT_FOUND = "Не удалось найти книгу с идентификатором %d";

	private static final String ID_PARAM = "id";
	private static final String TITLE_PARAM = "title";

	@PersistenceContext
	private EntityManager entityManager;
	
	//@org.springframework.data.jpa.repository.Query("select distinct b from Book b left join b.authors a left join b.genres g ")
	@Override
	public List<Book> getAll() {
		TypedQuery<Book> query = entityManager.createQuery(SIMPLE_BOOK_QUERY, Book.class);
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
		// получить старую модель
		Book oldBook = getById(book.getId());
		if (oldBook == null) {
			throw new IllegalStateException(BOOK_NOT_FOUND);
		}
		return entityManager.merge(book);
	}


	@Override
	public void delete(Long id) {
		Query query = entityManager.createQuery(DELETE_BOOK_QUERY);
		query.setParameter(ID_PARAM, id);
		query.executeUpdate();
	}

	@Override
	public Book getByTitle(String title) {
		TypedQuery<Book> query = entityManager.createQuery(SIMPLE_BOOK_QUERY + BOOK_WHERE_TITLE, Book.class);
		query.setParameter(TITLE_PARAM, title);
		return query.getSingleResult();
	}

	@Override
	public List<Book> search(String titleFilter) {
		TypedQuery<Book> query = entityManager.createQuery(SIMPLE_BOOK_QUERY + BOOK_WHERE_TITLE_LIKE, Book.class);
		query.setParameter(TITLE_PARAM, titleFilter);
		return query.getResultList();
	}
}
