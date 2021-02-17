package ru.gurkin.spring.library.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document("genres")
public class Genre {
	
	@Id
	private String id;
	
	private String title;

	public Genre(String title) {
		this(null, title);
	}

	@Override
	public String toString() {
		return "Genre{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Genre genre = (Genre) o;
		return Objects.equals(id, genre.id) && Objects.equals(title, genre.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}
}
