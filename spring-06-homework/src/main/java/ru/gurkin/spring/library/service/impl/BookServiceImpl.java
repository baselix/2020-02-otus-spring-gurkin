package ru.gurkin.spring.library.service.impl;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gurkin.spring.library.dao.BookDao;
import ru.gurkin.spring.library.dao.CommentDao;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.service.BookService;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Service
public class BookServiceImpl implements BookService{

	private final BookDao dao;
	private final CommentDao commentDao;

	public BookServiceImpl(BookDao dao, CommentDao commentDao) {
		this.dao = dao;
		this.commentDao = commentDao;
	}

	@Transactional
	@Override
	public List<Book> getAll() {
		return dao.getAll();
	}

	@Transactional
	@Override
	public Book getById(Long id) {
		checkNotNull(id, ID_ERROR);
		return dao.getById(id);
	}

	@Transactional
	@Override
	public Book create(Book book) {
		checkNotNull(book, BOOK_ERROR);
		checkArgument(book.getId() == null, NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(book.getTitle()), TITLE_ERROR);
		checkArgument(!book.getAuthors().isEmpty(), BOOK_NO_AUTHOR_ERROR);
		checkArgument(!book.getGenres().isEmpty(), BOOK_NO_GENRE_ERROR);
		return dao.create(book);
	}

	@Transactional
	@Override
	public Book update(Book book) {
		checkNotNull(book, BOOK_ERROR);
		checkArgument(book.getId() != null, NOT_NULL_ID_ERROR);
		checkArgument(!Strings.isNullOrEmpty(book.getTitle()), TITLE_ERROR);
		checkArgument(!book.getAuthors().isEmpty(), BOOK_NO_AUTHOR_ERROR);
		checkArgument(!book.getGenres().isEmpty(), BOOK_NO_GENRE_ERROR);
		return dao.update(book);
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
			dao.update(book);
			//удаляем комментарии
			for(Comment comment : book.getComments()) {
				commentDao.delete(comment.getId());
			}
			//завершаем удаление книги
			dao.delete(id);
		}
	}

	@Transactional
	@Override
	public List<Book> search(String titleFilter) {
		checkArgument(!Strings.isNullOrEmpty(titleFilter), TITLE_FILTER_ERROR);
		return dao.search(titleFilter);
	}

	@Transactional
	@Override
	public Book getByTitle(String title) {
		checkArgument(!Strings.isNullOrEmpty(title), TITLE_ERROR);
		return dao.getByTitle(title);
	}

}
