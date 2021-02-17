package ru.gurkin.spring.library.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", nullable = false, unique = true)
	private String title;

	public Genre(String title) {
		this(null, title);
	}

	public Genre(Long id, String title){
		this.id = id;
		this.title = title;
	}
}
