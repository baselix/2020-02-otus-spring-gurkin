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
 * Класс содержит данные для вычисленя результатов тестирования. Содержит список возможных результатов с верхней и нижней границами соответствующей им оценки
 * и список значений оценки для вариантов ответов.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TestResults {
	@NonNull
	private List<Result> results;
	@NonNull
	private List<ResultValue> resultValues;
}
