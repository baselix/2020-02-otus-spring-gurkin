/**
 * 
 */
package ru.gurkin.spring.usertesting.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author digurkin
 *
 * Вариант ответа и соответствующая ему оценка
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ResultValue {
	private String resultString;
	private int points;
}
