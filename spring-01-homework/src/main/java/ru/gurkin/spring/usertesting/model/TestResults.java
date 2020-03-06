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
 * Класс содержит данные для вычисленя результатов тестирования. Содержит список возможных результатов с верхней и нижней границами соответствующей им оценки
 * и список значений оценки для вариантов ответов.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TestResults {
	@NonNull
	@Builder.Default
	private List<Result> results = Lists.newArrayList();
	@NonNull
	@Builder.Default
	private List<ResultValue> resultValues = Lists.newArrayList();
}
