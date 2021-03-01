package ru.gurkin.spring.library.model;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "id", nullable = false, updatable = false, insertable = false)
	private Book book;
	
	@Column(name = "message", nullable = false)
	private String message;

	public Comment() {
	}

	public Comment(Long id, Book book, String message) {
		this.id = id;
		this.book = book;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", book=" + book +
				", message='" + message + '\'' +
				'}';
	}
}