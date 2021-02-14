package ru.gurkin.spring.journal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Document("users")
public class JournalUser {
	@Id
	private String id;

	@Indexed(unique = true)
	private String login;

	private String password;

	private UserRole role;

	public JournalUser(String login) {
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
