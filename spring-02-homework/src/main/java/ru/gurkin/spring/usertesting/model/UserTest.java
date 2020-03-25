/**
 * 
 */
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
 * Класс содержит данные теста: вопросы, строки приветствия и прощания, данные для вычисления результатов.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserTest {
	@NonNull
	private String greeting;
	@NonNull
	private String fareweel;
	@NonNull
	@Builder.Default
	private List<Question> questions = Lists.newArrayList();
	@NonNull
	@Builder.Default
	private TestResults testResults = new TestResults();
	private User user;
	private String testResult;
}
