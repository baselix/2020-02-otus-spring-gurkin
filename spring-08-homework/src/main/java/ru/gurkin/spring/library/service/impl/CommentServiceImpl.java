package ru.gurkin.spring.library.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.repository.CommentRepository;
import ru.gurkin.spring.library.service.CommentService;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Service
public class CommentServiceImpl implements CommentService{

	private final CommentRepository repository;
	
	public CommentServiceImpl(CommentRepository repository) {
		this.repository = repository;
	}

	@Override
	public Comment getById(String id) {
		checkNotNull(id, ID_ERROR);
		return repository.findById(id).
				orElse(null);
	}

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

	@Override
	public Comment update(Comment comment) {
		checkNotNull(comment, COMMENT_ERROR);
		checkArgument(comment.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(comment.getBook() != null, BOOK_ERROR);
		checkArgument(!Strings.isNullOrEmpty(comment.getMessage()), MESSAGE_ERROR);
		return repository.save(comment);
	}

	@Override
	public void delete(String id) {
		checkNotNull(id, ID_ERROR);
		repository.deleteById(id);
	}

	@Override
	public List<Comment> getCommentsByBook(Book book) {
		checkNotNull(book, BOOK_ERROR);
		return repository.findByBook(book);
	}

	@Override
	public List<Comment> getAllComments() {
		return repository.findAll();
	}

}
