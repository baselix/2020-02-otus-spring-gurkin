/**
 * 
 */
package ru.gurkin.spring.usertesting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import ru.gurkin.spring.usertesting.dao.TestServiceDao;
import ru.gurkin.spring.usertesting.dao.impl.CSVTestServiceDaoImpl;
import ru.gurkin.spring.usertesting.service.I18nService;
import ru.gurkin.spring.usertesting.service.impl.I18nServiceImpl;

/**
 * @author digurkin
 *
 */
@PropertySource("classpath:application.properties")
@Configuration
public class TestContextConfiguration {

	@Bean
    public MessageSource messageSource(@Value("${resources.test-file-name}")String fileName) {
    		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        	ms.setBasename("/i18n/" + fileName.substring(0, fileName.lastIndexOf(".")));
    		ms.setDefaultEncoding("UTF-8");
    		return ms;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    	return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean
    public I18nService i18nService(MessageSource messageSource, @Value("${application.locale}")String localeString) {
    		return new I18nServiceImpl(messageSource, localeString);
    }
    
    @Bean
    public TestServiceDao testServiceDao(I18nService i18nService, MessageSource messageSource, @Value("${resources.test-file-name}")String fileName) {
    		return new CSVTestServiceDaoImpl(i18nService, fileName);
    }
}
