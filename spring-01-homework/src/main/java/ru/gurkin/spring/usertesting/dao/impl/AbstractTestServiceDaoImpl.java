/**
 * 
 */
package ru.gurkin.spring.usertesting.dao.impl;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

import ru.gurkin.spring.usertesting.dao.TestServiceDao;
import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.Result;
import ru.gurkin.spring.usertesting.model.ResultValue;
import ru.gurkin.spring.usertesting.model.TestResults;
import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author digurkin
 * 
 * Абстрактная имплементация сервиса
 */
public abstract class AbstractTestServiceDaoImpl implements TestServiceDao{

	public static final String BAD_TEST_RESULT_STRING = "Не удалось вычислить результат тестирования. Обратитесь к разработчику.";
	
	public abstract UserTest getUserTest();
	
	public UserTest testResultsProcessing(UserTest test) {
		TestResults testResults = test.getTestResults();
		Map<String, Integer> answerPoints = testResults.getResultValues().stream().collect(Collectors.toMap(ResultValue::getResultString, ResultValue::getPoints));
		int resultSumm = 0;
		for(Question question : test.getQuestions()) {
			if(!Strings.isNullOrEmpty(question.getAnswer())) {
				String answer = question.getAnswer();
				if(answerPoints.containsKey(answer)) {
					resultSumm += answerPoints.get(answer);
				}
			}
		}
		String resultString = BAD_TEST_RESULT_STRING;
		for(Result result : testResults.getResults()) {
			if(resultSumm >= result.getLowerBorder() && resultSumm <= result.getUpperBorder()) {
				resultString = result.getResultString();
			}
		}
		test.setTestResult(resultString);
		return test;
	}
}
