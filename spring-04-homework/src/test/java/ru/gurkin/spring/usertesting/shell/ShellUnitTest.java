package ru.gurkin.spring.usertesting.shell;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;

import ru.gurkin.spring.usertesting.AbstractTest;
import ru.gurkin.spring.usertesting.events.LoginEvent;
import ru.gurkin.spring.usertesting.events.LogoutEvent;
import ru.gurkin.spring.usertesting.events.ShowResultEvent;
import ru.gurkin.spring.usertesting.events.StartTestingEvent;
import ru.gurkin.spring.usertesting.model.User;
import ru.gurkin.spring.usertesting.model.UserTest;
import ru.gurkin.spring.usertesting.service.impl.UserTestingServiceImpl;

@DisplayName("Тестирование команд shell: ")
@SpringBootTest
class ShellUnitTest extends AbstractTest{
	
	private static final String COMMAND_LOGIN = "login";
	private static final String COMMAND_LOGOUT = "logout";
	private static final String COMMAND_RESULT = "result";
	private static final String COMMAND_TEST = "test";
	
	@MockBean
	UserTestingServiceImpl userTestingService;
	
	@Autowired
	private Shell shell;
	
	@BeforeEach
	private void init() {
		when(userTestingService.getCurrentTest()).thenAnswer(new Answer<UserTest>() {

			@Override
			public UserTest answer(InvocationOnMock invocation) throws Throwable {
				UserTest test = getUserTest();
				test.setUser(new User("user", "soname"));
				test.setTestResult("testResult");
				return test;
			}
		});
	}
	
	@DisplayName(" login работает")
	@Test
	void loginTest() {
		doNothing().when(userTestingService).login(ArgumentMatchers.any(LoginEvent.class));
		shell.evaluate(() -> COMMAND_LOGIN);
		verify(userTestingService, times(1)).login(ArgumentMatchers.any(LoginEvent.class));
	}
	
	@DisplayName(" logout работает")
	@Test
	void logoutTest() {
		doNothing().when(userTestingService).logout(ArgumentMatchers.any(LogoutEvent.class));
		shell.evaluate(() -> COMMAND_LOGOUT);
		verify(userTestingService, times(1)).logout(ArgumentMatchers.any(LogoutEvent.class));
	}
	
	@DisplayName(" result работает")
	@Test
	void resultTest() {
		doNothing().when(userTestingService).showResult(ArgumentMatchers.any(ShowResultEvent.class));
		shell.evaluate(() -> COMMAND_RESULT);
		verify(userTestingService, times(1)).showResult(ArgumentMatchers.any(ShowResultEvent.class));
	}
	
	@DisplayName(" test работает")
	@Test
	void startTestingTest() {
		doNothing().when(userTestingService).startTesting(ArgumentMatchers.any(StartTestingEvent.class));
		shell.evaluate(() -> COMMAND_TEST);
		verify(userTestingService, times(1)).startTesting(ArgumentMatchers.any(StartTestingEvent.class));
	}

}
