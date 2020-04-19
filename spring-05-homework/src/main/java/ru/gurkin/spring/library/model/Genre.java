package ru.gurkin.spring.library.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Genre {
	private Long id;
	private String title;

	public Genre(String title) {
		this.title = title;
	}
}
