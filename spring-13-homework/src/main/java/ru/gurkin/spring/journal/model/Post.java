package ru.gurkin.spring.journal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document("posts")
public class Post {

	@Transient
	public static final String SEQUENCE_NAME = "posts_sequence";
	
	@Id
	private Long id;

	private String title;

	private String body;

	@Override
	public String toString() {
		return "Post{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", body=" + body +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		return Objects.equals(id, post.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
