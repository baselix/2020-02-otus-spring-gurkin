package ru.gurkin.spring.library.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
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

	@Override
	public Comment getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.getById(id);
	}

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

	@Override
	public Comment update(Comment comment) {
		checkNotNull(comment, COMMENT_ERROR);
		checkArgument(comment.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(comment.getBook() != null, BOOK_ID_ERROR);
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

	@Override
	public List<Comment> getAllComments() {
		return dao.getAll();
	}

}
