/**
 * 
 */
package ru.gurkin.spring.usertesting.service.impl;

import java.util.List;
import java.util.Scanner;

import com.google.common.base.Joiner;

import ru.gurkin.spring.usertesting.service.UserInterfaceService;

/**
 * @author digurkin
 */
public class UserInterfaceServiceImpl implements UserInterfaceService{

	private final Scanner scanner = new Scanner(System.in);

	@Override
	public String getUserName() {
		displayToUser("Введите ваше имя:");
		return getUserInput();
	}

	@Override
	public String getUserSoname() {
		displayToUser("Введите вашу фамилию:");
		return getUserInput();
	}

	@Override
	public String getUserInput() {
		return scanner.nextLine();
	}

	@Override
	public String getTemplatedUserInput(List<String> templateStrings) {
		displayToUser(String.format("Возможные варианты ответа: %s",Joiner.on(", ").join(templateStrings)));
		return getUserInput();
	}

	@Override
	public void displayToUser(String displayString) {
		System.out.println(displayString);
	}

}
