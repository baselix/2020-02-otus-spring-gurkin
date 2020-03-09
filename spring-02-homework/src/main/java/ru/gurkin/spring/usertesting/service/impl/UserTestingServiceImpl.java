/**
 * 
 */
package ru.gurkin.spring.usertesting.service.impl;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import ru.gurkin.spring.usertesting.dao.TestServiceDao;
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
	
	public UserTestingServiceImpl(UserInterfaceService userInterfaceService, TestServiceDao testService, I18nService i18nService) {
		this.userInterfaceService = userInterfaceService;
		this.testService = testService;
		this.i18nService = i18nService;
	}
	
	@Override
	public void startTesting() {
		UserTest test = testService.getUserTest();
		
		test.setUser(getUserData());
		
		displayGreeting(test.getGreeting());
		doTesting(test);
		displayTestingResults(test);
		displayFareweel(test.getFareweel());
	}
	
	private User getUserData() {
		userInterfaceService.displayDividingLineToUser();
		userInterfaceService.displayToUser(i18nService.getMessage(GREETING));
		String userName = userInterfaceService.getUserName();
		String userSoname = userInterfaceService.getUserSoname();
		return new User(userName, userSoname);
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
	
	private void doTesting(UserTest test) {
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
	
	private void displayTestingResults(UserTest test) {
		UserTest processedTest = testService.testResultsProcessing(test);
		if(!Strings.isNullOrEmpty(processedTest.getTestResult())){
			userInterfaceService.displayDividingLineToUser();
			userInterfaceService.displayToUser(i18nService.getMessage(RESULT_TEMPLATE, new Object[] {processedTest.getUser().getUserName(), processedTest.getTestResult()}));
		}
	}
}
