/**
 * 
 */
package ru.gurkin.spring.usertesting.service.impl;

import java.util.Scanner;

import ru.gurkin.spring.usertesting.service.UserInterfaceService;

/**
 * @author digurkin
 */
public class UserInterfaceServiceImpl implements UserInterfaceService{

	@Override
	public String getUserName() {
		return getUserInput();
	}

	@Override
	public String getUserSoname() {
		return getUserInput();
	}

	@Override
	public String getUserInput() {
		String userInput;
		try(Scanner scanner = new Scanner(System.in)){
			userInput = scanner.nextLine();
        }
		return userInput;
	}

	@Override
	public String getTemplatedUserInput(String templateString) {
		return getUserInput();
	}

	@Override
	public void displayToUser(String displayString) {
		System.out.println(displayString);
	}

}
