package ru.gurkin.spring.library.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.gurkin.spring.library.dao.CommentDao;
import ru.gurkin.spring.library.model.Comment;

@Transactional(propagation=Propagation.REQUIRED, readOnly=false, noRollbackFor=Exception.class)
@Repository
public class CommentDaoJpaImpl implements CommentDao {

	private static final String COMMENT_QUERY = "select distinct c from Comment c ";
	private static final String COMMENT_WHERE_ID = "where c.bookId = :id";
	private static final String DELETE_COMMENT_QUERY = "delete from Comment c where c.id = :id";

	private static final String ID_PARAM = "id";

	private static final String COMMENT_NOT_FOUND = "Не удалось найти комментарий";
	 
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public Comment getById(Long id) {
		return entityManager.find(Comment.class, id);
	}

	@Override
	public Comment create(Comment comment) {
		entityManager.persist(comment);
		return comment;
	}

	@Override
	public Comment update(Comment comment) {
		Comment old = getById(comment.getId());
		if (old == null) {
			throw new IllegalStateException(COMMENT_NOT_FOUND);
		}
		if (!old.getMessage().equals(comment.getMessage())) {
			return entityManager.merge(comment);
		}
		return comment;
	}

	@Override
	public void delete(Long id) {
		Query query = entityManager.createQuery(DELETE_COMMENT_QUERY);
		query.setParameter(ID_PARAM, id);
		query.executeUpdate();
	}

	@Override
	public List<Comment> getCommentsByBookId(Long id) {
		TypedQuery<Comment> query = entityManager.createQuery(COMMENT_QUERY + COMMENT_WHERE_ID, Comment.class);
		query.setParameter(ID_PARAM, id);
		return query.getResultList();
	}

	@Override
	public List<Comment> getAll() {
		TypedQuery<Comment> query = entityManager.createQuery(COMMENT_QUERY, Comment.class);
		return query.getResultList();
	}
}
