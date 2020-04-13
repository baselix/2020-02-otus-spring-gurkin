package ru.gurkin.spring.usertesting.shell;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import ru.gurkin.spring.usertesting.events.LoginEvent;
import ru.gurkin.spring.usertesting.events.LogoutEvent;
import ru.gurkin.spring.usertesting.events.ShowResultEvent;
import ru.gurkin.spring.usertesting.events.StartTestingEvent;
import ru.gurkin.spring.usertesting.service.UserTestingService;

@ShellComponent
public class ShellCommandsHolder {
	
	private final ApplicationEventPublisher applicationEventPublisher;
	private final UserTestingService userTestingService;

	public ShellCommandsHolder(ApplicationEventPublisher applicationEventPublisher, UserTestingService userTestingService) {
		this.applicationEventPublisher = applicationEventPublisher;
		this.userTestingService = userTestingService;
	}

	@ShellMethod(key = "login", value = "Login to user testing application")
	public void login() {
		applicationEventPublisher.publishEvent(new LoginEvent(this));
	}
	
	@ShellMethod(key = "logout", value = "Logout from user testing application")
	@ShellMethodAvailability("canLogout")
	public void logout() {
		applicationEventPublisher.publishEvent(new LogoutEvent(this));
	}
	
	@ShellMethod(key = "result", value = "Show tesitng result")
	@ShellMethodAvailability("canShowResult")
	public void showResult() {
		applicationEventPublisher.publishEvent(new ShowResultEvent(this));
	}
	
	@ShellMethod(key = "test", value = "Start user testing")
	@ShellMethodAvailability("canTesting")
	public void startUserTesting() {
		applicationEventPublisher.publishEvent(new StartTestingEvent(this));
	}
	
	@SuppressWarnings("unused")
	private Availability canTesting() {
		if (userTestingService.getCurrentTest() != null && userTestingService.getCurrentTest().getUser() != null) {
			return Availability.available();
		}else {
			return Availability.unavailable("Please, do login before");
		}
	}
	
	@SuppressWarnings("unused")
	private Availability canShowResult() {
		if (userTestingService.getCurrentTest() != null && userTestingService.getCurrentTest().getTestResult() != null) {
			return Availability.available();
		}else {
			return Availability.unavailable("Please, do test before");
		}
	}
	
	@SuppressWarnings("unused")
	private Availability canLogout() {
		if (userTestingService.getCurrentTest() != null && userTestingService.getCurrentTest().getUser() != null) {
			return Availability.available();
		}else {
			return Availability.unavailable("Please, do login before");
		}
	}
}
