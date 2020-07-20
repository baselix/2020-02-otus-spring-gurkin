package ru.gurkin.spring.library.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import ru.gurkin.spring.library.dao.BookDao;
import ru.gurkin.spring.library.dao.CommentDao;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.service.BookService;

import static com.google.common.base.Preconditions.*;
import static ru.gurkin.spring.library.model.ErrorConstants.*;

@Transactional(propagation=Propagation.REQUIRED, readOnly=false, noRollbackFor=Exception.class)
@Service
public class BookServiceImpl implements BookService{

	private final BookDao dao;
	private final CommentDao commentDao;

	public BookServiceImpl(BookDao dao, CommentDao commentDao) {
		this.dao = dao;
		this.commentDao = commentDao;
	}

	@Override
	public List<Book> getAll() {
		List<Book> books = dao.getAll(); 
		return books;
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
		Book createdBook = dao.create(book); 
		return createdBook;
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
		Book book = getById(id);
		if(book != null) {
			book.getAuthors().clear();
			book.getGenres().clear();
			//очищаем авторов и жанры
			dao.update(book);
			//удаляем комментарии
			for(Comment comment : commentDao.getCommentsByBookId(book.getId())) {
				commentDao.delete(comment.getId());
			}
			//завершаем удаление книги
			dao.delete(id);
		}
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
