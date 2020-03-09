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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import ru.gurkin.spring.usertesting.service.I18nService;
import ru.gurkin.spring.usertesting.service.UserInterfaceService;

/**
 * @author digurkin
 * 
 * Класс для взаимодействия с пользователем посредством консоли
 */
@Service
public class UserInterfaceServiceImpl implements UserInterfaceService{

	private Scanner scanner;
	private PrintStream out;
	
	private String systemEncoding;
	
	private static final String GET_NAME_STRING = "application.get-name-string";
	private static final String GET_SONAME_STRING = "application.get-soname-string";
	private static final String WRONG_ANSWER_TEMPLATE = "application.wrong-answer-template";
	private static final String EMPTY_ANSWER_STRING = "application.empty-answer-string";
	private static final String ANSWER_OPTIONS_TEMPLATE = "application.answer-options-template";
	private static final String DIVIDING_LINE = "application.dividing-line";
	
	private final I18nService i18nService;
	
	public UserInterfaceServiceImpl(@Value("${console.encoding}")String encoding, I18nService i18nService) {
		this.i18nService = i18nService;
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
		displayToUser(i18nService.getMessage(GET_NAME_STRING));
		return getUserInput();
	}

	@Override
	public String getUserSoname() {
		displayToUser(i18nService.getMessage(GET_SONAME_STRING));
		return getUserInput();
	}

	@Override
	public String getUserInput() {
		String result = scanner.nextLine();
		if(Strings.isNullOrEmpty(result)) {
			do {
				displayToUser(i18nService.getMessage(EMPTY_ANSWER_STRING));
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
		displayToUser(i18nService.getMessage(ANSWER_OPTIONS_TEMPLATE, new Object[] {templateString}));
		String result = getUserInput();
		if(!isRightInput(result, templateStrings)) {
			do {
				displayToUser(i18nService.getMessage(WRONG_ANSWER_TEMPLATE, new Object[] {result, templateString}));
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
		displayToUser(i18nService.getMessage(DIVIDING_LINE));
	}
	
	public void destroy() {
		scanner.close();
		out.close();
	}
	
}
