package ru.gurkin.spring.library.runner;

import org.h2.tools.Console;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Order(InteractiveShellApplicationRunner.PRECEDENCE - 1)
@Profile("!test")
public class H2ApplicationRunner implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Console.main("-tcp", "-tcpDaemon", "-web", "-browser");
	}
}
