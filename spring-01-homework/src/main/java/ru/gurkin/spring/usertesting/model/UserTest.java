/**
 * 
 */
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
 * Класс содержит данные теста: вопросы, строки приветствия и прощания, данные для вычисления результатов.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserTest {
	@NonNull
	private String greeting;
	private List<Question> questions;
	@NonNull
	private String fareweel;
	private User user;
	private String testResult;
	@NonNull
	private TestResults testResults;
}
