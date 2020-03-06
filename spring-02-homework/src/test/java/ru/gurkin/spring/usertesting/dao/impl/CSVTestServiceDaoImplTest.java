package ru.gurkin.spring.usertesting.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.gurkin.spring.usertesting.AbstractTest;
import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.UserTest;

class CSVTestServiceDaoImplTest extends AbstractTest{

	@Test
	void getUserTestTest() {
		CSVTestServiceDaoImpl csvService = new CSVTestServiceDaoImpl("test.csv");
		
		UserTest mockTest = getUserTest();
		UserTest readedTest = csvService.getUserTest();
		
		assertEquals(mockTest, readedTest);
		
	}
	
	@Test
	void testResultsProcessingTest() {
		CSVTestServiceDaoImpl csvService = new CSVTestServiceDaoImpl("test.csv"); 
		
		UserTest userTest = getUserTest();
		for(Question question : userTest.getQuestions()) {
			question.setAnswer("y");
		}
		userTest = csvService.testResultsProcessing(userTest);
		assertEquals("result2", userTest.getTestResult());
	}
	
	@Test
	void testResultsProcessingErrorTest() {
		CSVTestServiceDaoImpl csvService = new CSVTestServiceDaoImpl("test.csv"); 
		
		UserTest userTest = getUserTest();
		userTest = csvService.testResultsProcessing(userTest);
		assertEquals(CSVTestServiceDaoImpl.BAD_TEST_RESULT_STRING, userTest.getTestResult());
	}
	
}
