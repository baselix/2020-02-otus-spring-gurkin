package ru.gurkin.spring.library.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.BOOK_ID_ERROR;
import static ru.gurkin.spring.library.model.ErrorConstants.COMMENT_ERROR;
import static ru.gurkin.spring.library.model.ErrorConstants.ID_ERROR;
import static ru.gurkin.spring.library.model.ErrorConstants.MESSAGE_ERROR;
import static ru.gurkin.spring.library.model.ErrorConstants.NOT_NULL_ID_ERROR;
import static ru.gurkin.spring.library.model.ErrorConstants.NULL_ID_ERROR;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import ru.gurkin.spring.library.dao.CommentDao;
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.service.CommentService;

@Transactional(propagation=Propagation.REQUIRED, readOnly=false, noRollbackFor=Exception.class)
@Service
public class CommentServiceImpl implements CommentService{

	private final CommentDao dao;
	
	public CommentServiceImpl(CommentDao dao) {
		this.dao = dao;
	}

	@Override
	public Comment getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.getById(id);
	}

	@Override
	public Comment create(Comment comment) {
		checkNotNull(comment, COMMENT_ERROR);
		checkArgument(comment.getId() == null, NULL_ID_ERROR);
		checkArgument(comment.getBookId() != null, BOOK_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(comment.getMessage()), MESSAGE_ERROR);
		return dao.create(comment);
	}

	@Override
	public Comment update(Comment comment) {
		checkNotNull(comment, COMMENT_ERROR);
		checkArgument(comment.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(comment.getBookId() != null, BOOK_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(comment.getMessage()), MESSAGE_ERROR);
		return dao.update(comment);
	}

	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		dao.delete(id);
	}

	@Override
	public List<Comment> getCommentsByBookId(Long bookId) {
		checkNotNull(bookId, BOOK_ID_ERROR);
		return dao.getCommentsByBookId(bookId);
	}

}
