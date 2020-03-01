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
import com.google.common.collect.Lists;

import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.Result;
import ru.gurkin.spring.usertesting.model.ResultValue;
import ru.gurkin.spring.usertesting.model.TestResults;
import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author digurkin
 *
 * Сервис получения данных из файл .csv о тесте пользователей на воображение
 */
public class CSVTestServiceDaoImpl extends AbstractTestServiceDaoImpl{

	private static final String FILE_NOT_FOUND_TEMPLATE = "Файл с названием %s не найден";
	
	private static final String GREETING = "greeting";
	private static final String FAREWELL = "fareweel";
	private static final String QUESTION = "question";
	private static final String RESULT = "result";
	private static final String RESULT_VALUE = "resultValue";
	private static final char SEPARATOR = ';';
	private static final String ANSWER_OPTIONS_SEPARATOR = "|";
	
	private String fileName;

	public CSVTestServiceDaoImpl(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public UserTest getUserTest() {
		
		List<Question> questions = Lists.newArrayList();
		String greetingString = null;
		String fareweelString = null;

		List<Result> results =  Lists.newArrayList();
		List<ResultValue> resultValues = Lists.newArrayList();
		TestResults testResults = new TestResults(results, resultValues);
		
		Resource resource = new ClassPathResource(fileName);
		if(!resource.exists()) {
			throw new IllegalArgumentException(String.format(FILE_NOT_FOUND_TEMPLATE, fileName));
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
							question.setAnswerOptions(Splitter.on(ANSWER_OPTIONS_SEPARATOR).splitToList(iterator.next()));
						}
						questions.add(question);
						break;
						
					case RESULT:
						Result result = new Result("", 0, Integer.MAX_VALUE);
						if(iterator.hasNext()) {
							int lowerBorder = Integer.parseInt(iterator.next());
							result.setLowerBorder(lowerBorder);
						}
						if(iterator.hasNext()) {
							int upperBorder = Integer.parseInt(iterator.next());
							result.setUpperBorder(upperBorder);
						}
						if(iterator.hasNext()) {
							result.setResultString(iterator.next());
						}
						results.add(result);
						break;
						
					case RESULT_VALUE:
						ResultValue value = new ResultValue("", 0);
						if(iterator.hasNext()) {
							value.setResultString(iterator.next());
						}
						if(iterator.hasNext()) {
							int points = Integer.parseInt(iterator.next());
							value.setPoints(points);
						}
						resultValues.add(value);
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
		
		return new UserTest(greetingString, questions, fareweelString, null, null, testResults);
	}
}
