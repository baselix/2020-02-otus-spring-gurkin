package ru.gurkin.spring.library.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.repository.CommentRepository;
import ru.gurkin.spring.library.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	private final CommentRepository repository;
	
	public CommentServiceImpl(CommentRepository repository) {
		this.repository = repository;
	}

	@Transactional
	@Override
	public Comment getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return repository.findById(id).
				orElse(null);
	}

	@Transactional
	@Override
	public Comment create(Comment comment) {
		checkNotNull(comment, COMMENT_ERROR);
		checkArgument(comment.getId() == null, NULL_ID_ERROR);
		checkArgument(comment.getBook() != null, BOOK_ERROR);
		checkArgument(comment.getBook().getAuthors() != null, BOOK_NO_AUTHOR_ERROR);
		checkArgument(comment.getBook().getGenres() != null, BOOK_NO_GENRE_ERROR);
		checkArgument(!Strings.isNullOrEmpty(comment.getMessage()), MESSAGE_ERROR);
		return repository.save(comment);
	}

	@Transactional
	@Override
	public Comment update(Comment comment) {
		checkNotNull(comment, COMMENT_ERROR);
		checkArgument(comment.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(comment.getBook() != null, BOOK_ERROR);
		checkArgument(!Strings.isNullOrEmpty(comment.getMessage()), MESSAGE_ERROR);
		return repository.save(comment);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		repository.deleteById(id);
	}

	@Transactional
	@Override
	public List<Comment> getCommentsByBook(Book book) {
		checkNotNull(book, BOOK_ERROR);
		return repository.findByBook(book);
	}

	@Transactional
	@Override
	public List<Comment> getAllComments() {
		return (List<Comment>) repository.findAll();
	}

}
