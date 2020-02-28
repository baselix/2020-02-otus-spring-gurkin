package ru.gurkin.spring.usertesting;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.gurkin.spring.usertesting.service.UserTestingService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        UserTestingService userTestingService = context.getBean(UserTestingService.class);
        userTestingService.startTesting();
        context.close();
    }
}
