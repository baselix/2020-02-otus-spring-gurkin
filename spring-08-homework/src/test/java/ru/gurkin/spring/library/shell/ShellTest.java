package ru.gurkin.spring.library.shell;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static ru.gurkin.spring.library.model.ShellCommands.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.google.common.collect.Lists;

import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.service.AuthorService;
import ru.gurkin.spring.library.service.BookService;
import ru.gurkin.spring.library.service.GenreService;

@SpringBootTest(properties = { InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
		ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false" })
@ActiveProfiles("test")
@DisplayName("Тестирование команд shell: ")
class ShellTest {
	
	private static final String TITLE_PARAM = " title";
	private static final String NAME_PARAM = " name";
	private static final String ID_PARAM = " 1";
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private AuthorService authorService;
	
	@MockBean
	private GenreService genreService;
	
	@Autowired
	private Shell shell;
	
	@BeforeEach
	private void init() {
		when(authorService.getAll()).thenReturn(Lists.newArrayList(new Author()));
		when(genreService.getAll()).thenReturn(Lists.newArrayList(new Genre()));
		when(authorService.getById(ArgumentMatchers.any(String.class))).thenReturn(new Author());
		when(genreService.getById(ArgumentMatchers.any(String.class))).thenReturn(new Genre());
		when(bookService.getById(ArgumentMatchers.any(String.class))).thenReturn(new Book());
	}

	@Test
	@DisplayName(COMMAND_SHOW_GENRES)
	void showGenresTest() {
		shell.evaluate(() -> COMMAND_SHOW_GENRES);
		verify(genreService, times(1)).getAll();
	}
	
	@Test
	@DisplayName(COMMAND_CREATE_GENRE)
	void createGenreTest() {
		shell.evaluate(() -> COMMAND_CREATE_GENRE + TITLE_PARAM);
		verify(genreService, times(1)).create(ArgumentMatchers.any(Genre.class));
	}
	
	@Test
	@DisplayName(COMMAND_DELETE_GENRE)
	void deleteGenreTest() {
		shell.evaluate(() -> COMMAND_DELETE_GENRE + ID_PARAM);
		verify(genreService, times(1)).delete(ArgumentMatchers.any(String.class));
	}
	
	@Test
	@DisplayName(COMMAND_SHOW_AUTHORS)
	void showAuthorsTest() {
		shell.evaluate(() -> COMMAND_SHOW_AUTHORS);
		verify(authorService, times(1)).getAll();
	}
	
	@Test
	@DisplayName(COMMAND_CREATE_AUTHOR)
	void createAuthorTest() {
		shell.evaluate(() -> COMMAND_CREATE_AUTHOR + NAME_PARAM);
		verify(authorService, times(1)).create(ArgumentMatchers.any(Author.class));
	}
	
	@Test
	@DisplayName(COMMAND_DELETE_AUTHOR)
	void deleteAuthorTest() {
		shell.evaluate(() -> COMMAND_DELETE_AUTHOR + ID_PARAM);
		verify(authorService, times(1)).delete(ArgumentMatchers.any(String.class));
	}
	
	@Test
	@DisplayName(COMMAND_SHOW_BOOKS)
	void showBooksTest() {
		shell.evaluate(() -> COMMAND_SHOW_BOOKS);
		verify(bookService, times(1)).getAll();
	}
	
	@Test
	@DisplayName(COMMAND_CREATE_BOOK)
	void createBookTest() {
		shell.evaluate(() -> COMMAND_CREATE_BOOK + TITLE_PARAM + ID_PARAM + ID_PARAM);
		verify(bookService, times(1)).create(ArgumentMatchers.any(Book.class));
	}
	
	@Test
	@DisplayName(COMMAND_DELETE_BOOK)
	void deleteBookTest() {
		shell.evaluate(() -> COMMAND_DELETE_BOOK + ID_PARAM);
		verify(bookService, times(1)).delete(ArgumentMatchers.any(String.class));
	}
	
	@Test
	@DisplayName(COMMAND_ADD_GENRE)
	void addGenreTest() {
		shell.evaluate(() -> COMMAND_ADD_GENRE + ID_PARAM + ID_PARAM);
		verify(bookService, times(1)).update(ArgumentMatchers.any(Book.class));
	}
	
	@Test
	@DisplayName(COMMAND_REMOVE_GENRE)
	void removeGenreTest() {
		shell.evaluate(() -> COMMAND_REMOVE_GENRE + ID_PARAM + ID_PARAM);
		verify(bookService, times(1)).update(ArgumentMatchers.any(Book.class));
	}
	
	@Test
	@DisplayName(COMMAND_ADD_AUTHOR)
	void addAuthorTest() {
		shell.evaluate(() -> COMMAND_ADD_AUTHOR + ID_PARAM + ID_PARAM);
		verify(bookService, times(1)).update(ArgumentMatchers.any(Book.class));
	}
	
	@Test
	@DisplayName(COMMAND_REMOVE_AUTHOR)
	void removeAuthorTest() {
		shell.evaluate(() -> COMMAND_REMOVE_AUTHOR + ID_PARAM + ID_PARAM);
		verify(bookService, times(1)).update(ArgumentMatchers.any(Book.class));
	}
}
