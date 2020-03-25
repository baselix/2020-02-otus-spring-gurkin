package ru.gurkin.spring.usertesting.service;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;

public interface I18nService{
	void setLocale(Locale locale);
	String getMessage(String code, Object[] args) throws NoSuchMessageException;
	String getMessage(String code) throws NoSuchMessageException;
}
