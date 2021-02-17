package ru.gurkin.spring.library.model;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "book_id", nullable = false, updatable = false)
	private Book book;
	
	@Column(name = "message", nullable = false)
	private String message;
}
