package ru.gurkin.spring.usertesting.events;

import org.springframework.context.ApplicationEvent;

public class ShowResultEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1073856686880040983L;

	public ShowResultEvent(Object source) {
		super(source);
	}

}
