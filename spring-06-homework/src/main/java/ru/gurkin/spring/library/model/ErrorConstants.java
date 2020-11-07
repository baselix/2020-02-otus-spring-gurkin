package ru.gurkin.spring.library.model;

public class ErrorConstants {

	public static final String NAME_FILTER_ERROR = "NameFilter must be not null or empty";
	public static final String TITLE_FILTER_ERROR = "TitleFilter must be not null or empty";
	public static final String AUTHOR_ERROR = "Author is null";
	public static final String BOOK_ERROR = "Book is null";
	public static final String GENRE_ERROR = "Genre is null";
	public static final String COMMENT_ERROR = "Comment is null";
	public static final String NULL_ID_ERROR = "Id must be null";
	public static final String NOT_NULL_ID_ERROR = "Id must be not null";
	public static final String ID_ERROR = "Id is null";
	public static final String NAME_ERROR = "Name must be not null or empty";
	public static final String TITLE_ERROR = "Title must be not null or empty";
	public static final String BOOK_ID_ERROR = "BookId is null";
	public static final String MESSAGE_ERROR = "Message must be not null or empty";
	public static final String BOOK_NO_AUTHOR_ERROR = "Book needs an author";
	public static final String BOOK_NO_GENRE_ERROR = "Book needs an genre";

	private ErrorConstants(){}
}
