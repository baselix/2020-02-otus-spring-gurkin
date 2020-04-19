package ru.gurkin.spring.library.model;

import java.util.Set;

import com.google.common.collect.Sets;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Book {
	private Long id;
	private String title;
	private Set<Author> authors = Sets.newHashSet();
	private Set<Genre> genres = Sets.newHashSet();

	public Book(String title) {
		this.title = title;
	}

	public Book(Long id, String title) {
		this(title);
		this.id = id;
	}

	public Book(String title, Author author, Genre genre) {
		this(title);
		if (author != null) {
			this.authors.add(author);
		}
		if (genre != null) {
			this.genres.add(genre);
		}
	}
}
