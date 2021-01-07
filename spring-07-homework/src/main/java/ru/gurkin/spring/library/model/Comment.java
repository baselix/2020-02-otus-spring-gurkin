package ru.gurkin.spring.library.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "book_id", nullable = false, updatable = false)
	private Book book;
	
	@Column(name = "message", nullable = false)
	private String message;

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", book=" + book +
				", message='" + message + '\'' +
				'}';
	}
}
