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
@ConfigurationProperties("console")
public class ConsoleConfig {
	private String encoding;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
