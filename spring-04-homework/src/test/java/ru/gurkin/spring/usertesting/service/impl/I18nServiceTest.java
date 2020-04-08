package ru.gurkin.spring.usertesting.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.gurkin.spring.usertesting.AbstractTest;
import ru.gurkin.spring.usertesting.service.I18nService;

@ActiveProfiles("test")
@SpringBootTest
public class I18nServiceTest extends AbstractTest{

	@Autowired
	I18nService i18nService;
	
	private static final String GREETING_CODE = "test.greeting";
	private static final String GREETING = "greeting";
	private static final String GREETING_RU = "greeting_ru";
	
	@Test
	public void getMessageTest() {
		i18nService.setLocale(Locale.forLanguageTag("en"));
		String greeting = i18nService.getMessage(GREETING_CODE);
		assertEquals(GREETING, greeting);
		i18nService.setLocale(Locale.forLanguageTag("ru"));
		greeting = i18nService.getMessage(GREETING_CODE);
		assertEquals(GREETING_RU, greeting);
	}
}
