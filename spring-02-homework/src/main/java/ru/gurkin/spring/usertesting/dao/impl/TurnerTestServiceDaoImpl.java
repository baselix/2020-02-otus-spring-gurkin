/**
 * 
 */
package ru.gurkin.spring.usertesting.dao.impl;

import java.util.List;

import com.google.common.collect.Lists;

import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.Result;
import ru.gurkin.spring.usertesting.model.ResultValue;
import ru.gurkin.spring.usertesting.model.TestResults;
import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author digurkin
 *
 * Класс возвращает данные о тесте "Насколько хороший вы токарь"
 */
public class TurnerTestServiceDaoImpl extends AbstractTestServiceDaoImpl{
	
	private static final String GREETING = "Добро пожаловать в тест \"Насколько хороший вы токарь\"";
	private static final String FAREWELL = "Всего доброго. Благодарим за использование теста \"Насколько хороший вы токарь\"";

	@Override
	public UserTest getUserTest() {
		List<Question> questions = Lists.newArrayList(new Question("Сколько пальцев на вашей левой руке?", Lists.newArrayList("0", "1","2","3","4","5", "6"), null));
		TestResults testResults = new TestResults(
				Lists.newArrayList(
					new Result("К сожалению, у вас что-то явно пошло не так. Вы отвратительный токарь.", 0, 0),
					new Result("К сожалению, у вас что-то явно пошло не так. Но могло быть и хуже. Поверьте.", 1, 1),
					new Result("К сожалению, у вас что-то явно пошло не так. Но у вас еще осталась пара попыток стать токарем.", 2, 2),
					new Result("К сожалению, у вас что-то явно пошло не так. Но вы все еще можете стать токарем.", 3, 3),
					new Result("Внезапно у вас что-то пошло не так. Но вы, скорее всего, токарь.", 4, 4),
					new Result("Вы или очень хороший токарь, или токарем никогда не были.", 5, 5),
					new Result("Похоже, вы редкостный мутант и, скорее всего, токарем никогда не были", 6, 6)
				),Lists.newArrayList(
					new ResultValue("0", 0), 
					new ResultValue("1", 1), 
					new ResultValue("2", 2), 
					new ResultValue("3", 3), 
					new ResultValue("4", 4), 
					new ResultValue("5", 5), 
					new ResultValue("6", 6)
				));
		return new UserTest(GREETING, FAREWELL, questions, testResults, null, null);
	}

}
