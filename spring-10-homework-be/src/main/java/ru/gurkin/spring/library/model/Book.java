package ru.gurkin.spring.library.model;

import com.google.common.collect.Sets;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", nullable = false, unique = true)
	private String title;
	
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "book_author",
			joinColumns = {
					@JoinColumn(name = "book_id", referencedColumnName = "id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "author_id", referencedColumnName = "id")
			})
	private Set<Author> authors = Sets.newHashSet();
	
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "book_genre",
			joinColumns = {
					@JoinColumn(name = "book_id", referencedColumnName = "id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "genre_id", referencedColumnName = "id")
			})
	private Set<Genre> genres = Sets.newHashSet();
}
