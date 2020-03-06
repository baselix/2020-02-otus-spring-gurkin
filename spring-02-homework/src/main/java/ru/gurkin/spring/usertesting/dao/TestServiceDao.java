/**
 * 
 */
package ru.gurkin.spring.usertesting.dao;

import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author digurkin
 *
 * Сервис для получения данных теста
 */
public interface TestServiceDao {
	
	/**
	 * Метод возвращает модель UserTest с заполненными данными теста
	 * 
	 * @return UserTest
	 */
	UserTest getUserTest();	
	
	/**
	 * Метод принимает заполненную ответами модель теста и возвращает модель теста с заполненным результатом тестирования
	 * 
	 * @return UserTest
	 */
	UserTest testResultsProcessing(UserTest test);
}
