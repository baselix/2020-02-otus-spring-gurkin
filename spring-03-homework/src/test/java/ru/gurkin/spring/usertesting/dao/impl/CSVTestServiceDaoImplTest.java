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
	
//	private UserTest getUserTest() {
//		UserTest userTest = new UserTest();
//		userTest.setFareweel("fareweel");
//		userTest.setGreeting("greeting");
//		List<Question> questions = Lists.newArrayList(
//				new Question("question1", Lists.newArrayList("answer1","answer2"), null),
//				new Question("question2", Lists.newArrayList(), null)
//				);
//		userTest.setQuestions(questions);
//		TestResults testResults = new TestResults(
//				Lists.newArrayList(
//					new Result("result1", 1, 1),
//					new Result("result2", 100, 2)
//				),Lists.newArrayList(
//					new ResultValue("answer1", 1), 
//					new ResultValue("answer2", 0)
//				));
//		userTest.setTestResults(testResults);
//		return userTest;
//	}
}
