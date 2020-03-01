/**
 * 
 */
package ru.gurkin.spring.usertesting.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import ru.gurkin.spring.usertesting.service.UserInterfaceService;

/**
 * @author digurkin
 */
public class UserInterfaceServiceImpl implements UserInterfaceService{

	private Scanner scanner;
	private PrintStream out;
	
	private String systemEncoding;
	
	private static final String GET_NAME_STRING = "Введите ваше имя:";
	private static final String GET_SONAME_STRING = "Введите вашу фамилию:";
	private static final String WRONG_ANSWER_TEMPLATE = "Недопустимое значение(%s). Выберите один из предложенных вариантов: %s";
	private static final String EMPTY_ANSWER_STRING = "Необходимо ввести хоть что-то. Попробуйте еще раз.";
	private static final String ANSWER_OPTIONS_TEMPLATE = "Возможные варианты ответа: %s";
	private static final String DIVIDING_LINE = "---------------------------------";
	
	public UserInterfaceServiceImpl(String encoding) {
		try {
			this.systemEncoding = System.getProperty("console.encoding", encoding);
			this.scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			this.out = new PrintStream(System.out, true, systemEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getUserName() {
		displayToUser(GET_NAME_STRING);
		return getUserInput();
	}

	@Override
	public String getUserSoname() {
		displayToUser(GET_SONAME_STRING);
		return getUserInput();
	}

	@Override
	public String getUserInput() {
		String result = scanner.nextLine();
		if(Strings.isNullOrEmpty(result)) {
			do {
				displayToUser(EMPTY_ANSWER_STRING);
				result = scanner.nextLine();
			}while(Strings.isNullOrEmpty(result));
		}
		try {
			result = new String(result.trim().getBytes(), systemEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getTemplatedUserInput(List<String> templateStrings) {
		String templateString = Joiner.on(", ").join(templateStrings);
		displayToUser(String.format(ANSWER_OPTIONS_TEMPLATE, templateString));
		String result = getUserInput();
		if(!isRightInput(result, templateStrings)) {
			do {
				displayToUser(String.format(WRONG_ANSWER_TEMPLATE, result, templateString));
				result = getUserInput();
			}while(!isRightInput(result, templateStrings));
		}
		return result;
	}

	@Override
	public void displayToUser(String displayString) {
		out.println(displayString);
	}

	private boolean isRightInput(String input, List<String> rightInputs) {
		if(!Strings.isNullOrEmpty(input)) {
			Set<String> rightInputsSet = Sets.newHashSet(rightInputs);
			if(rightInputsSet.contains(input)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void displayDividingLineToUser() {
		displayToUser(DIVIDING_LINE);
	}
	
	public void destroy() {
		scanner.close();
		out.close();
	}
	
}
