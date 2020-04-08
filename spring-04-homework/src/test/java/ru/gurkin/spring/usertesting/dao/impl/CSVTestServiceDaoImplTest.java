package ru.gurkin.spring.usertesting.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.gurkin.spring.usertesting.AbstractTest;
import ru.gurkin.spring.usertesting.dao.TestServiceDao;
import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.UserTest;
import ru.gurkin.spring.usertesting.service.I18nService;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("Тест .csv файла")
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
