package ru.gurkin.spring.usertesting.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * @author digurkin
 * 
 * Вопрос теста. Содержит текст вопроса и варианты ответа (опционально)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Question {
	@NonNull
	private String question;
	private List<String> answerOptions;
	private String answer;
}
