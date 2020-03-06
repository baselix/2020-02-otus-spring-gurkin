/**
 * 
 */
package ru.gurkin.spring.usertesting.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * @author digurkin
 * 
 * Описывает один из возможных результатов тестирования. Состоит из строки результата и верхней и нижней границ оценки
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Result {
	@NonNull
	private String resultString;
	private int upperBorder;
	private int lowerBorder;
}
