/**
 * 
 */
package ru.gurkin.spring.usertesting.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author digurkin
 *
 */
@Component
@ConfigurationProperties("application")
public class ApplicationConfig {
	private String locale;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String toString() {
		return locale.toString();
	}

}
