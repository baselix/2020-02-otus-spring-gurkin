package ru.gurkin.spring.library.model;

import com.google.common.collect.Sets;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document("books")
public class Book {
	
	@Id
	private String id;
	
	private String title;

	@DBRef
	private Set<Author> authors = Sets.newHashSet();

	@DBRef
	private Set<Genre> genres = Sets.newHashSet();

	@Override
	public String toString() {
		return "Book{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", authors=" + authors +
				", genres=" + genres +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
		return Objects.equals(id, book.id) && Objects.equals(title, book.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}
}
