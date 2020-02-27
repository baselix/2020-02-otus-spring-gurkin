/**
 * 
 */
package ru.gurkin.spring.usertesting.service.impl;

import ru.gurkin.spring.usertesting.service.UserInterfaceService;
import ru.gurkin.spring.usertesting.service.UserTestingService;

/**
 * @author digurkin
 *
 * Сервис для тестирования воображения у пользователей
 */
public class UserImaginationTestingServiceImpl implements UserTestingService{
	
	private static final String GREETING = "Доброго времени суток";
	private static final String FAREWELL = "Всего доброго. Благодарим за использование теста на воображение";
	private static final String COPYRIGHT = "© Дмитрий Гуркин, 2020";

	private final UserInterfaceService userInterfaceService;
	
	public UserImaginationTestingServiceImpl(UserInterfaceService userInterfaceService) {
		this.userInterfaceService = userInterfaceService;
	}
	
	@Override
	public void startTesting() {
		userInterfaceService.displayToUser(GREETING);
		//TODO: добавить получение данных пользователя
		//TODO: добавить вывод шапки теста
		//TODO: добавить вывод вопросов и получение ответа от пользователя
		//TODO: добавить вычисление результатов тестирования (возможно, стоит получать граничные данные из самого теста)
		//TODO: добавить вывод результата пользователю
		userInterfaceService.displayToUser(FAREWELL);
		userInterfaceService.displayToUser(COPYRIGHT);
	}
}
