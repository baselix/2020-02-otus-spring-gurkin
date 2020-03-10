/**
 * 
 */
package ru.gurkin.spring.usertesting;

import java.util.List;

import com.google.common.collect.Lists;

import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.Result;
import ru.gurkin.spring.usertesting.model.ResultValue;
import ru.gurkin.spring.usertesting.model.TestResults;
import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author 
 *
 */
public abstract class AbstractTest {

	protected UserTest getUserTest() {
		UserTest userTest = new UserTest();
		userTest.setFareweel("fareweel");
		userTest.setGreeting("greeting");
		List<Question> questions = Lists.newArrayList(
				new Question("question1", Lists.newArrayList("answer1","answer2"), null),
				new Question("question2", Lists.newArrayList(), null)
				);
		userTest.setQuestions(questions);
		TestResults testResults = new TestResults(
				Lists.newArrayList(
					new Result("result1", 1, 1),
					new Result("result2", 100, 2)
				),Lists.newArrayList(
					new ResultValue("answer1", 1), 
					new ResultValue("answer2", 0)
				));
		userTest.setTestResults(testResults);
		return userTest;
	}
}
