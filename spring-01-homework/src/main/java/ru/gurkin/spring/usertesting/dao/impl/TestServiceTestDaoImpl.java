/**
 * 
 */
package ru.gurkin.spring.usertesting.dao.impl;

import java.util.List;

import com.google.common.collect.Lists;

import ru.gurkin.spring.usertesting.dao.TestServiceDao;
import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author digurkin
 *
 * Тестовый класс реализации сервиса получения данных о тесте
 */
public class TestServiceTestDaoImpl implements TestServiceDao{
	
	private static final String GREETING = "Доброго времени суток";
	private static final String FAREWELL = "Всего доброго. Благодарим за использование теста \"Насколько хороший вы токарь\"";

	@Override
	public UserTest getUserTest() {
		List<Question> questions = Lists.newArrayList(new Question("Сколько пальцев на вашей левой руке?", Lists.newArrayList("0", "1","2","3","4","5", "6"), null));
		return new UserTest(GREETING, questions, FAREWELL, null, null);
	}

	@Override
	public UserTest testResultsProcessing(UserTest test) {
		String answer = test.getQuestions().get(0).getAnswer();
		String result;
		switch (answer) {
			case "0":
				result = "К сожалению, у вас что-то явно пошло не так. Вы отвратительный токарь.";
				break;
			case "1":
				result = "К сожалению, у вас что-то явно пошло не так. Но могло быть и хуже. Поверьте.";
				break;
			case "2":
				result = "К сожалению, у вас что-то явно пошло не так. Но у вас еще осталась пара попыток стать токарем.";
				break;
			case "3":
				result = "К сожалению, у вас что-то явно пошло не так. Но вы все еще можете стать токарем.";
				break;
			case "4":
				result = "Внезапно у вас что-то пошло не так. Но вы, скорее всего, токарь.";
				break;
			case "5":
				result = "Вы или очень хороший токарь, или токарем никогда не были.";
				break;
			case "6":
			default:
				result = "Похоже, вы редкостный мутант и, скорее всего, токарем никогда не были";
				break;
		}
		test.setTestResult(result);
		return test;
	}
}
