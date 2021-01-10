package ru.gurkin.spring.library.runner;

import org.h2.tools.Console;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class H2ApplicationRunner implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Console.main("-tcp", "-tcpDaemon", "-web", "-browser");
	}
}
