/**
 * 
 */
package ru.gurkin.spring.usertesting.service;

import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author digurkin
 *
 * Служба тестирования пользователей
 */
public interface UserTestingService {
	
	/**
	 * Метод, запускающий тестирование пользователя
	 */
	void startTesting();
	
	/**
	 * Метод, возвращающий текущую модель теста
	 */
	UserTest getCurrentTest();
}
