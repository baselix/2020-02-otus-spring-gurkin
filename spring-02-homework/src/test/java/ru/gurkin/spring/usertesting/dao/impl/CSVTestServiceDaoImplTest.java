package ru.gurkin.spring.usertesting.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.gurkin.spring.usertesting.AbstractTest;
import ru.gurkin.spring.usertesting.TestContextConfiguration;
import ru.gurkin.spring.usertesting.dao.TestServiceDao;
import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.UserTest;
import ru.gurkin.spring.usertesting.service.I18nService;

@TestPropertySource("/application.properties")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContextConfiguration.class})
class CSVTestServiceDaoImplTest extends AbstractTest{
	
	@Autowired
	TestServiceDao testService;
	
	@Autowired
	I18nService i18nService;
	
	@BeforeEach
	void init() {
	}
	
	@Test
	void getUserTestTest() {
		UserTest mockTest = getUserTest();
		UserTest readedTest = testService.getUserTest();
		assertEquals(mockTest, readedTest);
	}
	
	@Test
	void testResultsProcessingTest() {
		UserTest userTest = getUserTest();
		for(Question question : userTest.getQuestions()) {
			question.setAnswer(i18nService.getMessage("answer.1"));
		}
		userTest = testService.testResultsProcessing(userTest);
		assertEquals(i18nService.getMessage("result.2"), userTest.getTestResult());
	}
	
	@Test
	void testResultsProcessingErrorTest() {
		
		UserTest userTest = getUserTest();
		userTest = testService.testResultsProcessing(userTest);
		assertEquals(i18nService.getMessage("application.bad_test_result_string"), userTest.getTestResult());
	}
}
