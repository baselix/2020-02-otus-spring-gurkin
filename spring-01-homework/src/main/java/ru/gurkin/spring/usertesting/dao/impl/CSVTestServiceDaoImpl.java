/**
 * 
 */
package ru.gurkin.spring.usertesting.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import ru.gurkin.spring.usertesting.model.Question;
import ru.gurkin.spring.usertesting.model.Result;
import ru.gurkin.spring.usertesting.model.ResultValue;
import ru.gurkin.spring.usertesting.model.UserTest;

/**
 * @author digurkin
 *
 * Сервис получения данных теста из файла .csv 
 */
public class CSVTestServiceDaoImpl extends AbstractTestServiceDaoImpl{

	private static final String FILE_NOT_FOUND_TEMPLATE = "Файл с названием %s не найден";
	
	private static final String GREETING = "greeting";
	private static final String FAREWELL = "fareweel";
	private static final String QUESTION = "question";
	private static final String RESULT = "result";
	private static final String RESULT_VALUE = "resultValue";
	private static final String ANSWER_OPTIONS_SEPARATOR = "|";
	private static final String EMPTY_STRING = "";
	
	public static final String DEFAULT_RESOURCE_ENCODING = "UTF-8";
	public static final char DEFAULT_SEPARATOR = ';';
	
	private final String fileName;
	private final String resourceEncoding;
	private final char separator;

	public CSVTestServiceDaoImpl(String fileName, String resourceEncoding, char separator) {
		this.fileName = fileName;
		if(Strings.isNullOrEmpty(resourceEncoding)) {
			this.resourceEncoding = DEFAULT_RESOURCE_ENCODING;
		}else {
			this.resourceEncoding = resourceEncoding;
		}
		this.separator = separator;
	}
	
	public CSVTestServiceDaoImpl(String fileName, String resourceEncoding) {
		this(fileName, resourceEncoding, DEFAULT_SEPARATOR);
	}
	
	public CSVTestServiceDaoImpl(String fileName) {
		this(fileName, DEFAULT_RESOURCE_ENCODING, DEFAULT_SEPARATOR);
	}
	
	@Override
	public UserTest getUserTest() {
		return readTestData(getResource(fileName), separator, resourceEncoding);
	}
	
	private Resource getResource(String fileName) {
		Resource resource = new ClassPathResource(fileName);
		if(!resource.exists()) {
			throw new IllegalArgumentException(String.format(FILE_NOT_FOUND_TEMPLATE, fileName));
		}
		return resource;
	}
	
	private UserTest readTestData(Resource resource, char separator, String encoding) {
		UserTest userTest = new UserTest();
		try (CSVParser csvParser = new CSVParser(new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8")), CSVFormat.newFormat(DEFAULT_SEPARATOR))){
			for (CSVRecord csvRecord : csvParser) {
				Iterator<String> iterator = csvRecord.iterator();
				if(iterator.hasNext()) {
					String type = iterator.next();
					switch (type) {
						case QUESTION:
							userTest.getQuestions().add(readQuestion(iterator));
							break;
							
						case RESULT:
							userTest.getTestResults().getResults().add(readResult(iterator));
							break;
							
						case RESULT_VALUE:
							userTest.getTestResults().getResultValues().add(readResultValue(iterator));
							break;
							
						case GREETING:
							userTest.setGreeting(readGreeting(iterator));
							break;
							
						case FAREWELL:
							userTest.setFareweel(readFareweel(iterator));
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
		return userTest;
	}
	
	private Question readQuestion(Iterator<String> iterator) {
		Question question = new Question();
		if(iterator.hasNext()) {
			question.setQuestion(iterator.next());
		}
		if(iterator.hasNext()) {
			question.setAnswerOptions(Splitter.on(ANSWER_OPTIONS_SEPARATOR).splitToList(iterator.next()));
		}
		return question;
	}
	
	private Result readResult(Iterator<String> iterator) {
		Result result = new Result(EMPTY_STRING, 0, Integer.MAX_VALUE);
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
		return result;
	}
	
	private ResultValue readResultValue(Iterator<String> iterator) {
		ResultValue value = new ResultValue(EMPTY_STRING, 0);
		if(iterator.hasNext()) {
			value.setResultString(iterator.next());
		}
		if(iterator.hasNext()) {
			int points = Integer.parseInt(iterator.next());
			value.setPoints(points);
		}
		return value;
	}
	
	private String readGreeting(Iterator<String> iterator) {
		return iterator.next();
	}
	
	private String readFareweel(Iterator<String> iterator) {
		return iterator.next();
	}
}
