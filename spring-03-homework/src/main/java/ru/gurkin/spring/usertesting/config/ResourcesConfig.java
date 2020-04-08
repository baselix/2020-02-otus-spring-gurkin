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
@ConfigurationProperties("resources")
public class ResourcesConfig {
	private String testFileName;

	public String getTestFileName() {
		return testFileName;
	}

	public void setTestFileName(String testFileName) {
		this.testFileName = testFileName;
	}
	
	public String toString() {
		return testFileName.toString();
	}
}
