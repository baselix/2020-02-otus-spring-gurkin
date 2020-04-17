DROP TABLE IF EXISTS book_genre;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS books;

CREATE TABLE books (
	id IDENTITY(1001) PRIMARY KEY,
	title VARCHAR(250) NOT NULL
);

CREATE TABLE authors (
	id IDENTITY(1001) PRIMARY KEY,
	name VARCHAR(250) NOT NULL
);

CREATE TABLE genres (
	id IDENTITY(1001) PRIMARY KEY,
	title VARCHAR(250) NOT NULL
);

CREATE TABLE book_genre (
	book_id integer NOT NULL,
    genre_id integer NOT NULL,
    PRIMARY KEY (book_id , genre_id ),
    FOREIGN KEY (book_id) REFERENCES books,
    FOREIGN KEY (genre_id ) REFERENCES genres
);

CREATE TABLE book_author (
	book_id integer NOT NULL,
    author_id integer NOT NULL,
    PRIMARY KEY (book_id , author_id ),
    FOREIGN KEY (book_id) REFERENCES books,
    FOREIGN KEY (author_id ) REFERENCES authors
);

ALTER TABLE books ADD CONSTRAINT BOOK_TITLE_UNIQUE UNIQUE(title);
ALTER TABLE authors ADD CONSTRAINT AUTHOR_NAME_UNIQUE UNIQUE(name);
ALTER TABLE genres ADD CONSTRAINT GENRE_TITLE_UNIQUE UNIQUE(title);