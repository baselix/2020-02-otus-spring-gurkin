package ru.gurkin.spring.library.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.dao.CommentDao;
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.service.CommentService;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Service
public class CommentServiceImpl implements CommentService{

	private final CommentDao dao;
	
	public CommentServiceImpl(CommentDao dao) {
		this.dao = dao;
	}

	@Transactional
	@Override
	public Comment getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.getById(id);
	}

	@Transactional
	@Override
	public Comment create(Comment comment) {
		checkNotNull(comment, COMMENT_ERROR);
		checkArgument(comment.getId() == null, NULL_ID_ERROR);
		checkArgument(comment.getBook() != null, BOOK_ID_ERROR);
		checkArgument(comment.getBook().getAuthors() != null, BOOK_ID_ERROR);
		checkArgument(comment.getBook().getGenres() != null, BOOK_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(comment.getMessage()), MESSAGE_ERROR);
		return dao.create(comment);
	}

	@Transactional
	@Override
	public Comment update(Comment comment) {
		checkNotNull(comment, COMMENT_ERROR);
		checkArgument(comment.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(comment.getBook() != null, BOOK_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(comment.getMessage()), MESSAGE_ERROR);
		return dao.update(comment);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		dao.delete(id);
	}

	@Transactional
	@Override
	public List<Comment> getAllComments() {
		return dao.getAll();
	}

}
