package ru.gurkin.spring.library.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.repository.BookRepository;
import ru.gurkin.spring.library.repository.CommentRepository;
import ru.gurkin.spring.library.service.BookService;

import static com.google.common.base.Preconditions.*;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Service
public class BookServiceImpl implements BookService{

	private final BookRepository dao;
	private final CommentRepository commentDao;

	public BookServiceImpl(BookRepository dao, CommentRepository commentDao) {
		this.dao = dao;
		this.commentDao = commentDao;
	}

	@Override
	public List<Book> getAll() {
		return (List<Book>) dao.findAll();
	}

	@Override
	public Book getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.findById(id)
				.orElse(null);
	}

	@Transactional
	@Override
	public Book create(Book book) {
		checkNotNull(book, BOOK_ERROR);
		checkArgument(book.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(book.getTitle()), TITLE_ERROR);
		checkArgument(!book.getAuthors().isEmpty(), BOOK_NO_AUTHOR_ERROR);
		checkArgument(!book.getGenres().isEmpty(), BOOK_NO_GENRE_ERROR);
		return dao.save(book);
	}

	@Transactional
	@Override
	public Book update(Book book) {
		checkNotNull(book, BOOK_ERROR);
		checkArgument(book.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(book.getTitle()), TITLE_ERROR);
		checkArgument(!book.getAuthors().isEmpty(), BOOK_NO_AUTHOR_ERROR);
		checkArgument(!book.getGenres().isEmpty(), BOOK_NO_GENRE_ERROR);
		return dao.save(book);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		Book book = getById(id);
		if(book != null) {
			book.getAuthors().clear();
			book.getGenres().clear();
			//очищаем авторов и жанры
			dao.save(book);
			//удаляем комментарии
			for(Comment comment : commentDao.findByBook(book)) {
				commentDao.delete(comment);
			}
			//завершаем удаление книги
			dao.delete(book);
		}
	}

	@Override
	public List<Book> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return dao.findByTitleLike(titleFilter);
	}

	@Override
	public Book getByTitle(String title) {
		checkArgument(!Strings.isNullOrEmpty(title), TITLE_ERROR);
		return dao.findByTitle(title);
	}

}
