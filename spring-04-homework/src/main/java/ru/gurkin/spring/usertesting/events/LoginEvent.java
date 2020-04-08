package ru.gurkin.spring.usertesting.events;

import org.springframework.context.ApplicationEvent;

public class LoginEvent extends ApplicationEvent{

	private static final long serialVersionUID = 4341308923885529182L;

	public LoginEvent(Object source) {
		super(source);
	}

}
