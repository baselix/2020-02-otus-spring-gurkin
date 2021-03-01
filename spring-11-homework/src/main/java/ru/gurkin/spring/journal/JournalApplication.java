package ru.gurkin.spring.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.model.Post;
import ru.gurkin.spring.journal.model.UserRole;
import ru.gurkin.spring.journal.repository.PostRepository;
import ru.gurkin.spring.journal.repository.UserRepository;

import java.util.List;

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
			user1 = userRepository.save(user).block();
		}

		JournalUser user2 = userRepository.findByLoginIgnoreCase("user2").block();
		if(user2 == null){
			JournalUser user = new JournalUser("user2");
			user.setPassword("user2");
			user.setRole(UserRole.USER);
			user2 = userRepository.save(user).block();
		}

		PostRepository postRepository = context.getBean(PostRepository.class);

		List<Post> posts1 = postRepository.findByUser(Mono.just(user1.getLogin())).collectList().block();
		if(posts1.isEmpty()){
			Post post = new Post();
			post.setTitle("First post from User1");
			post.setBody("This is my first post. Enjoy, gays");
			post.setUser(user1.getLogin());
			postRepository.save(post);
		}

		List<Post> posts2 = postRepository.findByUser(Mono.just(user2.getLogin())).collectList().block();
		if(posts2.isEmpty()){
			Post post = new Post();
			post.setTitle("First post from User2");
			post.setBody("This is my first post. Enjoy, gays");
			post.setUser(user2.getLogin());
			postRepository.save(post);
		}
	}
}
