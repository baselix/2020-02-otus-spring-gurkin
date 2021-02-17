package ru.gurkin.spring.journal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document("posts")
public class Post {
	
	@Id
	private String id;
	
	private String title;

	private String body;

	private String user;

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
		return Objects.equals(id, post.id) && Objects.equals(title, post.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}
}
