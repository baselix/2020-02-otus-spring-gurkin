package ru.gurkin.spring.library.model;

import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "comments")
@NamedEntityGraph(name = "graph.Comment", attributeNodes = {@NamedAttributeNode(value = "book")})
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Book book;
	
	@Column(name = "message", nullable = false)
	private String message;
}