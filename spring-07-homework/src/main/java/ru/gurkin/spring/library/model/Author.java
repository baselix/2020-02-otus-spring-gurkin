package ru.gurkin.spring.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
	private Set<Book> books = new HashSet<>();

	public Author(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Author{" +
				"id=" + id +
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
