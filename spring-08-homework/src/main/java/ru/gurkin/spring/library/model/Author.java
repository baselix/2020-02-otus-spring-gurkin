package ru.gurkin.spring.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Document("authors")
public class Author {
	@Id
	private String id;
	
	private String name;

	public Author(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Author{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Author author = (Author) o;
		return Objects.equals(id, author.id) && Objects.equals(name, author.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
