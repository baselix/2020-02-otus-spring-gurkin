/**
 * 
 */
package ru.gurkin.spring.usertesting.service.impl;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import ru.gurkin.spring.usertesting.dao.TestServiceDao;
import ru.gurkin.spring.usertesting.events.LoginEvent;
import ru.gurkin.spring.usertesting.events.LogoutEvent;
import ru.gurkin.spring.usertesting.events.ShowResultEvent;
import ru.gurkin.spring.usertesting.events.StartTestingEvent;
import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.User;
import ru.gurkin.spring.usertesting.model.UserTest;
import ru.gurkin.spring.usertesting.service.I18nService;
import ru.gurkin.spring.usertesting.service.UserInterfaceService;
import ru.gurkin.spring.usertesting.service.UserTestingService;

/**
 * @author digurkin
 *
 * Сервис для тестирования пользователей
 */
@Service
public class UserTestingServiceImpl implements UserTestingService{
	
	private static final String GREETING = "application.greeting";
	private static final String RESULT_TEMPLATE = "application.result-template";
	private static final String COPYRIGHT = "application.copyright";

	private final UserInterfaceService userInterfaceService;
	private final TestServiceDao testService;
	private final I18nService i18nService;
	
	private UserTest test;
	
	public UserTestingServiceImpl(UserInterfaceService userInterfaceService, TestServiceDao testService, I18nService i18nService) {
		this.userInterfaceService = userInterfaceService;
		this.testService = testService;
		this.i18nService = i18nService;
	}
	
	@Override
	public void startTesting() {
		getTestData();
		getUserData();
		displayGreeting(test.getGreeting());
		doTesting();
		displayTestingResults();
		displayFareweel(test.getFareweel());
	}
	
	private void getTestData() {
		test = testService.getUserTest();
	}
	
	private void getUserData() {
		userInterfaceService.displayDividingLineToUser();
		userInterfaceService.displayToUser(i18nService.getMessage(GREETING));
		String userName = userInterfaceService.getUserName();
		String userSoname = userInterfaceService.getUserSoname();
		test.setUser(new User(userName, userSoname));
	}
	
	private void displayGreeting(String greeting) {
		userInterfaceService.displayDividingLineToUser();
		userInterfaceService.displayToUser(greeting);
	}
	
	private void displayFareweel(String fareweel) {
		userInterfaceService.displayDividingLineToUser();
		userInterfaceService.displayToUser(fareweel);
		userInterfaceService.displayToUser(i18nService.getMessage(COPYRIGHT));
		userInterfaceService.displayDividingLineToUser();
	}
	
	private void doTesting() {
		for(Question question : test.getQuestions()) {
			userInterfaceService.displayDividingLineToUser();
			userInterfaceService.displayToUser(question.getQuestion());
			if(question.getAnswerOptions() != null && !question.getAnswerOptions().isEmpty()) {
				question.setAnswer(userInterfaceService.getTemplatedUserInput(question.getAnswerOptions()));
			}else {
				question.setAnswer(userInterfaceService.getUserInput());
			}
		}
	}
	
	private void displayTestingResults() {
		test = testService.testResultsProcessing(test);
		if(!Strings.isNullOrEmpty(test.getTestResult())){
			userInterfaceService.displayDividingLineToUser();
			userInterfaceService.displayToUser(i18nService.getMessage(RESULT_TEMPLATE, new Object[] {test.getUser().getUserName(), test.getTestResult()}));
		}
	}

	@Override
	public UserTest getCurrentTest() {
		return test;
	}
	
	@EventListener
	public void login(LoginEvent loginEvent) {
		getTestData();
		getUserData();
	}
	
	@EventListener
	public void logout(LogoutEvent logoutEvent) {
		getTestData();
	}
	
	@EventListener
	public void startTesting(StartTestingEvent startTestingEvent) {
		displayGreeting(test.getGreeting());
		doTesting();
		displayTestingResults();
		displayFareweel(test.getFareweel());
	}
	
	@EventListener
	public void showResult(ShowResultEvent showResultEvent) {
		displayTestingResults();
	}
}
