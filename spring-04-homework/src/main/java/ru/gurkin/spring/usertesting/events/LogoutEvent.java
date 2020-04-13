package ru.gurkin.spring.usertesting.events;

import org.springframework.context.ApplicationEvent;

public class LogoutEvent extends ApplicationEvent{

	private static final long serialVersionUID = 5331973918204781132L;

	public LogoutEvent(Object source) {
		super(source);
	}

}
