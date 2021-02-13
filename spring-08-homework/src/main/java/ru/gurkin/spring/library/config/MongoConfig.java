package ru.gurkin.spring.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("ru.gurkin.spring.library.repository")
@Configuration
public class MongoConfig {

}
