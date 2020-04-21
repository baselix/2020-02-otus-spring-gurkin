package ru.gurkin.spring.library.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import ru.gurkin.spring.library.dao.BookDao;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.service.BookService;

import static com.google.common.base.Preconditions.*;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Service
public class BookServiceImpl implements BookService{

	private final BookDao dao;

	public BookServiceImpl(BookDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Book> getAll() {
		return dao.getAll();
	}

	@Override
	public Book getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.getById(id);
	}

	@Override
	public Book create(Book book) {
		checkNotNull(book, BOOK_ERROR);
		checkArgument(book.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(book.getTitle()), TITLE_ERROR);
		checkArgument(book.getAuthors().size() > 0, BOOK_NO_AUTHOR_ERROR);
		checkArgument(book.getGenres().size() > 0, BOOK_NO_GENRE_ERROR);
		return dao.create(book);
	}

	@Override
	public Book update(Book book) {
		checkNotNull(book, BOOK_ERROR);
		checkArgument(book.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(book.getTitle()), TITLE_ERROR);
		checkArgument(book.getAuthors().size() > 0, BOOK_NO_AUTHOR_ERROR);
		checkArgument(book.getGenres().size() > 0, BOOK_NO_GENRE_ERROR);
		return dao.update(book);
	}

	@Override
	public void delete(Long id) {
		checkNotNull(id, ID_ERROR);
		dao.delete(id);
	}

	@Override
	public List<Book> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return dao.search(titleFilter);
	}

	@Override
	public Book getByTitle(String title) {
		checkArgument(!Strings.isNullOrEmpty(title), TITLE_ERROR);
		return dao.getByTitle(title);
	}

}
