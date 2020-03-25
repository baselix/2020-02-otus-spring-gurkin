package ru.gurkin.spring.usertesting.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import ru.gurkin.spring.usertesting.service.I18nService;

@Service
public class I18nServiceImpl implements I18nService{

	private final MessageSource messageSource;
	private Locale locale;
	
    public I18nServiceImpl(MessageSource messageSource, @Value("${application.locale}")String localeString) {
		this.messageSource = messageSource;
		
		if(!Strings.isNullOrEmpty(localeString)) {
			this.locale = Locale.forLanguageTag(localeString);
		}else {
			this.locale = Locale.getDefault();
		}
    }

	@Override
	public String getMessage(String code, Object[] args) throws NoSuchMessageException {
		return messageSource.getMessage(code, args, getLocale());
	}
	
	@Override
	public String getMessage(String code) throws NoSuchMessageException {
		return getMessage(code, null);
	}

	private Locale getLocale() {
		return this.locale;
	}

	@Override
	public void setLocale(Locale locale) {
		if(locale != null) {
			this.locale = locale;
		}
	}
}
