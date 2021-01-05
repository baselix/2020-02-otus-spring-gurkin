package ru.gurkin.spring.library.dao.impl;

import org.springframework.stereotype.Repository;
import ru.gurkin.spring.library.dao.CommentDao;
import ru.gurkin.spring.library.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDaoJpaImpl implements CommentDao {

	private static final String COMMENT_QUERY = "select c from Comment c ";

	@PersistenceContext
	private final EntityManager entityManager;

	public CommentDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


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
		return entityManager.merge(comment);
	}

	@Override
	public void delete(Long id) {
		Comment comment = getById(id);
		if(comment != null) {
			entityManager.remove(comment);
		}
	}

	@Override
	public List<Comment> getAll() {
		TypedQuery<Comment> query = entityManager.createQuery(COMMENT_QUERY, Comment.class);
		return query.getResultList();
	}
}
