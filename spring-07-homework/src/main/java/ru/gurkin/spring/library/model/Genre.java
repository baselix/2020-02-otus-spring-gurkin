package ru.gurkin.spring.library.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "genres")
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", nullable = false, unique = true)
	private String title;

	@ManyToMany(mappedBy = "genres")
	private Set<Book> books = new HashSet<>();

	public Genre(String title) {
		this(null, title);
	}

	public Genre(Long id, String title){
		this.id = id;
		this.title = title;
	}

	@Override
	public String toString() {
		return "Genre{" +
				"id=" + id +
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
