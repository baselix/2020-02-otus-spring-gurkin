package ru.gurkin.spring.usertesting.events;

import org.springframework.context.ApplicationEvent;

public class StartTestingEvent extends ApplicationEvent{

	private static final long serialVersionUID = -6472874714243519914L;

	public StartTestingEvent(Object source) {
		super(source);
	}

}
