package ru.gurkin.spring.usertesting.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import ru.gurkin.spring.usertesting.service.UserTestingService;

@Component
@Profile("!test")
public class UserTestingCommandLineRunner implements CommandLineRunner{

	private final GenericApplicationContext context;
	
	public UserTestingCommandLineRunner(GenericApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public void run(String... args) throws Exception {
		UserTestingService userTestingService = context.getBean(UserTestingService.class);
        userTestingService.startTesting();
        context.close();
	}

}
