package ru.gurkin.spring.usertesting.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import ru.gurkin.spring.usertesting.AbstractTest;
import ru.gurkin.spring.usertesting.dao.TestServiceDao;
import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.UserTest;
import ru.gurkin.spring.usertesting.service.I18nService;
import ru.gurkin.spring.usertesting.service.UserInterfaceService;

@SpringBootTest
class UserTestingServiceImplTest extends AbstractTest{
	
	@Mock
	UserInterfaceService userInterfaceService;
	
	@Mock
	TestServiceDao testService;
	
	@Mock
	I18nService i18nService;
	
	@InjectMocks
	UserTestingServiceImpl userTestingService;
	
	private static final String USER_NAME = "User";
	private static final String USER_SONAME = "Userov";
	private static final String ANSWER = "answer1";
	private static final String RESULT = "result2";
	
	@BeforeEach
	void prepareData() {
		when(testService.getUserTest()).thenReturn(getUserTest());
		when(userInterfaceService.getUserName()).thenReturn(USER_NAME);
		when(userInterfaceService.getUserSoname()).thenReturn(USER_SONAME);
		when(userInterfaceService.getUserInput()).thenReturn(ANSWER);
		when(userInterfaceService.getTemplatedUserInput(ArgumentMatchers.anyList())).thenReturn(ANSWER);
		when(testService.testResultsProcessing(ArgumentMatchers.any(UserTest.class))).thenAnswer(new Answer<UserTest>() {

			@Override
			public UserTest answer(InvocationOnMock invocation) throws Throwable {
				UserTest userTest = invocation.getArgument(0);
				userTest.setTestResult(RESULT);
				return userTest;
			}
		});
	}
		
	@Test
	void startTestingTest() {
		userTestingService.startTesting();
		UserTest resultTest = userTestingService.getCurrentTest();
		assertEquals(resultTest.getTestResult(), RESULT);
		assertEquals(resultTest.getUser().getUserName(), USER_NAME);
		assertEquals(resultTest.getUser().getUserSoname(), USER_SONAME);
		for(Question question : resultTest.getQuestions()) {
			assertEquals(question.getAnswer(), ANSWER);
		}
	}
}
