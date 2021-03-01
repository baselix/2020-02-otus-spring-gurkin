package ru.gurkin.spring.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.model.UserRole;
import ru.gurkin.spring.journal.repository.PostRepository;
import ru.gurkin.spring.journal.repository.UserRepository;

@SpringBootApplication
public class JournalApplication {
	public static void main(String[] args){
		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);

		JournalUser user1 = userRepository.findByLoginIgnoreCase("user1").block();
		if(user1 == null){
			JournalUser user = new JournalUser("user1");
			user.setPassword("user1");
			user.setRole(UserRole.USER);
			userRepository.save(user).block();
		}

		JournalUser user2 = userRepository.findByLoginIgnoreCase("user2").block();
		if(user2 == null){
			JournalUser user = new JournalUser("user2");
			user.setPassword("user2");
			user.setRole(UserRole.USER);
			userRepository.save(user).block();
		}
	}
}
