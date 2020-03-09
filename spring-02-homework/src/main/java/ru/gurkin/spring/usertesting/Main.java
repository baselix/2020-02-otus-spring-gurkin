package ru.gurkin.spring.usertesting;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ru.gurkin.spring.usertesting.service.UserTestingService;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        UserTestingService userTestingService = context.getBean(UserTestingService.class);
        userTestingService.startTesting();
        context.close();
    }
}
