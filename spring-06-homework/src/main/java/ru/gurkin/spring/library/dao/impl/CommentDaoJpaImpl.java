package ru.gurkin.spring.library.dao.impl;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.dao.CommentDao;
import ru.gurkin.spring.library.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDaoJpaImpl implements CommentDao {

	private static final String COMMENT_QUERY = "select c from Comment c ";
	private static final String COMMENT_WHERE_ID = "where c.book.id = :id";

	private static final String ID_PARAM = "id";

	private static final String COMMENT_NOT_FOUND = "Не удалось найти комментарий";
	 
	@PersistenceContext
	private final EntityManager entityManager;

	public CommentDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Transactional
	@EntityGraph(value = "graph.Comment")
	@Override
	public Comment getById(Long id) {
		return entityManager.find(Comment.class, id);
	}

	@Transactional
	@EntityGraph(value = "graph.Comment")
	@Override
	public Comment create(Comment comment) {
		entityManager.persist(comment);
		return comment;
	}

	@Transactional
	@EntityGraph(value = "graph.Comment")
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

	@Transactional
	@Override
	public void delete(Long id) {
		Comment comment = getById(id);
		if(comment != null) {
			entityManager.remove(comment);
		}
	}

	@Transactional
	@EntityGraph(value = "graph.Comment")
	@Override
	public List<Comment> getCommentsByBookId(Long id) {
		TypedQuery<Comment> query = entityManager.createQuery(COMMENT_QUERY + COMMENT_WHERE_ID, Comment.class);
		query.setParameter(ID_PARAM, id);
		return query.getResultList();
	}

	@Transactional
	@EntityGraph(value = "graph.Comment")
	@Override
	public List<Comment> getAll() {
		TypedQuery<Comment> query = entityManager.createQuery(COMMENT_QUERY, Comment.class);
		return query.getResultList();
	}
}
