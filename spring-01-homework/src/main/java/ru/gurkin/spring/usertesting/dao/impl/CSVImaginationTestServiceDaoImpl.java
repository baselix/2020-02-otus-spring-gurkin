/**
 * 
 */
package ru.gurkin.spring.usertesting.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import ru.gurkin.spring.usertesting.dao.TestServiceDao;
import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author digurkin
 *
 * Сервис получения данных из файл .csv о тесте пользователей на воображение
 */
public class CSVImaginationTestServiceDaoImpl implements TestServiceDao{

	private static final String GREETING = "greeting";
	private static final String FAREWELL = "fareweel";
	private static final String QUESTION = "question";
	private static final char SEPARATOR = ';';
	
	private String fileName;

	public CSVImaginationTestServiceDaoImpl(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public UserTest getUserTest() {
		
		List<Question> questions = Lists.newArrayList();
		String greetingString = null;
		String fareweelString = null;
		
		Resource resource = new ClassPathResource(fileName);
		if(!resource.exists()) {
			throw new IllegalArgumentException(String.format("Файл с названием %s не найден", fileName));
		}
		
		try (CSVParser csvParser = new CSVParser(new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8")), CSVFormat.newFormat(SEPARATOR))){
			for (CSVRecord csvRecord : csvParser) {
				Iterator<String> iterator = csvRecord.iterator();
				if(iterator.hasNext()) {
					String type = iterator.next();
					switch (type) {
					case QUESTION:
						Question question = new Question();
						if(iterator.hasNext()) {
							question.setQuestion(iterator.next());
						}
						if(iterator.hasNext()) {
							question.setAnswerOptions(Splitter.on("|").splitToList(iterator.next()));
						}
						questions.add(question);
						break;
						
					case GREETING:
						greetingString = iterator.next();
						break;
						
					case FAREWELL:
						fareweelString = iterator.next();
						break;
						
					default:
						break;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new UserTest(greetingString, questions, fareweelString, null, null);
	}

	@Override
	public UserTest testResultsProcessing(UserTest test) {
		int resultSumm = 0;
		for(Question question : test.getQuestions()) {
			if(!Strings.isNullOrEmpty(question.getAnswer())) {
				String answer = question.getAnswer();
				switch (answer) {
					case "да":
						resultSumm += 2;
						break;
					case "незнаю":
						resultSumm += 1;
						break;
					case "нет":
					default:
						break;
				}
			}
		}
		String result;
		if(resultSumm >= 27 && resultSumm <= 40) {
			result = "Вы живете в фантастическом мире, исключительно богатом деталями. \"Спускаясь на землю\", Вы оказываетесь на чужой территории.";
		}else if(resultSumm >= 13 && resultSumm <= 26) {
			result = "У Вас случаются отдельные вспышки интуиции. Но Ваша фантазия находится в зависимости от настроения.";
		}else {
			result = "Ваши отличительные черты -\"приземленность\" и прагматичность. Опираясь на твердую почву, Вы не можете от нее оторваться, а потому почти лишены дара предвидения.";
		}
		test.setTestResult(result);
		return test;
	}

}
