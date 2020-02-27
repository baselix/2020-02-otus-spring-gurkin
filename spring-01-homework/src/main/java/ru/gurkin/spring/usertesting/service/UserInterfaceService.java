/**
 * 
 */
package ru.gurkin.spring.usertesting.service;

/**
 * @author digurkin
 * 
 * Интерфейс, предоставляющий методы для взаимодействия с пользователем
 */
public interface UserInterfaceService {
	/**
	 * Метод, возвращающий имя пользователя
	 * @return userName
	 */
	String getUserName();
	
	/**
	 * Метод, возвращающий Фамилию
	 * @return userSoname
	 */
	String getUserSoname();
	
	/**
	 * Метод, возвращающий произвольный пользовательский ввод
	 * @return userName
	 */
	String getUserInput();
	
	/**
	 * Метод, возвращающий пользовательский ввод, совпадающий с одним из вариантов, перечисленных в templateString
	 * @param templateString - строка шаблона. содержит допустимые ответы пользователя, разделенные '|'
	 * @return userInput
	 */
	String getTemplatedUserInput(String templateString);
	
	/**
	 * Метод, отображающий пользователю переданную в него строку
	 * @param displayString - строка для отображения пользователю
	 */
	void displayToUser(String displayString);
}
