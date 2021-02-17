package ru.gurkin.spring.library.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document("comments")
public class Comment {

	@Id
	private String id;

	@DBRef(lazy = true)
	private Book book;
	
	private String message;

	@Override
	public String toString() {
		return "Comment{" +
				"id='" + id + '\'' +
				", book=" + book +
				", message='" + message + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Comment comment = (Comment) o;
		return Objects.equals(id, comment.id) && Objects.equals(book.getId(), comment.book.getId()) && Objects.equals(message, comment.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, book.getId(), message);
	}
}
