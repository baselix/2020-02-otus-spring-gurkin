package ru.gurkin.spring.journal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document("users")
public class JournalUser {

	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private Long id;

	@Indexed(unique = true)
	private String login;

	private String password;

	private UserRole role;

	public JournalUser(String login) {
		this.login = login;
	}

	public JournalUser(Long id, String login) {
		this.id = id;
		this.login = login;
	}

	@Override
	public String toString() {
		return "JournalUser{" +
				"id='" + id + '\'' +
				", login='" + login + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		JournalUser journalUser = (JournalUser) o;
		return Objects.equals(id, journalUser.id) && Objects.equals(login, journalUser.login);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login);
	}
}
