package ru.gurkin.spring.usertesting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import ru.gurkin.spring.usertesting.service.UserTestingService;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {
	
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        UserTestingService userTestingService = context.getBean(UserTestingService.class);
        userTestingService.startTesting();
        context.close();
    }
    
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
}
