package ru.gurkin.spring.library.shell;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.service.AuthorService;
import ru.gurkin.spring.library.service.BookService;
import ru.gurkin.spring.library.service.GenreService;

import static ru.gurkin.spring.library.model.ShellCommands.*;

@ShellComponent
@ShellCommandGroup("Library operations")
public class ShellCommandsHolder {

	private static final String AUTHOR_GROUP = "1: Author operations";
	private static final String GENRE_GROUP = "2: Genre operations";
	private static final String BOOK_GROUP = "3: Book operations";

	private static final String CANT_DELETE_AUTHOR = "Нельзя удалить используемого автора";
	private static final String CANT_DELETE_BOOK = "Нельзя удалить книжку, почему-то";
	private static final String CANT_DELETE_GENRE = "Нельзя удалить используемый жанр";

	private static final String NEED_AUTHOR_OR_GENRE = "Необходимо создать хотя бы одного автора и хотя бы один жанр";

	private static final String NO_AUTHOR = "Нет ни одного автора";
	private static final String NO_GENRE = "Нет ни одного жанра";

	private static final String OK_MESSAGE = "ok";

	private final GenreService genreService;
	private final BookService bookService;
	private AuthorService authorService;

	public ShellCommandsHolder(BookService bookService, AuthorService authorService, GenreService genreService) {
		this.bookService = bookService;
		this.authorService = authorService;
		this.genreService = genreService;
	}

	@ShellMethod(key = COMMAND_SHOW_GENRES, value = "Show all genres", group = GENRE_GROUP)
	public Object showGenres() {
		try {
			return genreService.getAll();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_CREATE_GENRE, value = "Create genre", group = GENRE_GROUP)
	public Object createGenre(@ShellOption() String title) {
		try {
			return genreService.create(new Genre(title));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_DELETE_GENRE, value = "Delete genre", group = GENRE_GROUP)
	@ShellMethodAvailability("hasGenres")
	public Object deleteGenre(@ShellOption() Long id) {
		try {
			genreService.delete(id);
			return OK_MESSAGE;
		} catch (DataIntegrityViolationException dive) {
			return CANT_DELETE_GENRE;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_SHOW_AUTHORS, value = "Show all authors", group = AUTHOR_GROUP)
	public Object showAuthors() {
		try {
			return authorService.getAll();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_CREATE_AUTHOR, value = "Create author", group = AUTHOR_GROUP)
	public Object createAuthor(@ShellOption() String name) {
		try {
			return authorService.create(new Author(name));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_DELETE_AUTHOR, value = "Delete author", group = AUTHOR_GROUP)
	@ShellMethodAvailability("hasAuthors")
	public Object deleteAuthor(@ShellOption() Long id) {
		try {
			authorService.delete(id);
			return OK_MESSAGE;
		} catch (DataIntegrityViolationException dive) {
			return CANT_DELETE_AUTHOR;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_SHOW_BOOKS, value = "Show all books", group = BOOK_GROUP)
	public Object showBooks() {
		try {
			return bookService.getAll();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_CREATE_BOOK, value = "Create book", group = BOOK_GROUP)
	@ShellMethodAvailability("canCreateBook")
	public Object createBook(@ShellOption() String title, @ShellOption() Long authorId, @ShellOption() Long genreId) {
		try {
			Author author = authorService.getById(authorId);
			Genre genre = genreService.getById(genreId);
			return bookService.create(new Book(title, author, genre));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_DELETE_BOOK, value = "Delete book", group = BOOK_GROUP)
	public Object deleteBook(@ShellOption() Long id) {
		try {
			bookService.delete(id);
			return OK_MESSAGE;
		} catch (DataIntegrityViolationException dive) {
			return CANT_DELETE_BOOK;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_ADD_AUTHOR, value = "Add author to book", group = BOOK_GROUP)
	public Object addAuthor(@ShellOption() Long bookId, @ShellOption() Long authorId) {
		try {
			Book book = bookService.getById(bookId);
			Author author = authorService.getById(authorId);
			book.getAuthors().add(author);
			return bookService.update(book);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_REMOVE_AUTHOR, value = "Remove author from book", group = BOOK_GROUP)
	public Object removeAuthor(@ShellOption() Long bookId, @ShellOption() Long authorId) {
		try {
			Book book = bookService.getById(bookId);
			Author author = authorService.getById(authorId);
			book.getAuthors().remove(author);
			return bookService.update(book);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_ADD_GENRE, value = "Add genre to book", group = BOOK_GROUP)
	public Object addGenre(@ShellOption() Long bookId, @ShellOption() Long genreId) {
		try {
			Book book = bookService.getById(bookId);
			Genre genre = genreService.getById(genreId);
			book.getGenres().add(genre);
			return bookService.update(book);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ShellMethod(key = COMMAND_REMOVE_GENRE, value = "Remove genre from book", group = BOOK_GROUP)
	public Object removeGenre(@ShellOption() Long bookId, @ShellOption() Long genreId) {
		try {
			Book book = bookService.getById(bookId);
			Genre genre = genreService.getById(genreId);
			book.getGenres().remove(genre);
			return bookService.update(book);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private Availability hasAuthors() {
		if (!authorService.getAll().isEmpty()) {
			return Availability.available();
		} else {
			return Availability.unavailable(NO_AUTHOR);
		}
	}

	private Availability hasGenres() {
		if (!genreService.getAll().isEmpty()) {
			return Availability.available();
		} else {
			return Availability.unavailable(NO_GENRE);
		}
	}

	@SuppressWarnings("unused")
	private Availability canCreateBook() {
		if (hasAuthors().isAvailable() && hasGenres().isAvailable()) {
			return Availability.available();
		} else {
			return Availability.unavailable(NEED_AUTHOR_OR_GENRE);
		}
	}
}
