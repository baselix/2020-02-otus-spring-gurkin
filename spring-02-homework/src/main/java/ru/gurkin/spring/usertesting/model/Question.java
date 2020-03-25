package ru.gurkin.spring.usertesting.model;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
	@Builder.Default
	private List<String> answerOptions = Lists.newArrayList();
	private String answer;
}
