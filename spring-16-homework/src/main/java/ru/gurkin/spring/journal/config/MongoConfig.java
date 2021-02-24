package ru.gurkin.spring.journal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("ru.gurkin.spring.journal.repository")
@Configuration
public class MongoConfig {

}
