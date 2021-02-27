package ru.gurkin.spring.journal.repository;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gurkin.spring.journal.model.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DisplayName("Репозиторий записей корректно ")
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("получает все записи")
	void getAllTest() {
		Post newPost = new Post();
		newPost.setId(1L);
		newPost.setTitle("new post");
		postRepository.save(newPost);
		List<Post> allPosts = postRepository.findAll();
		for(Post post : allPosts) {
			System.out.println(post);
		}
		assertEquals(1, allPosts.size());
	}

	@Test
	@DisplayName("получает запись по заголовку")
	void geteByTitleTest() {
		Post newPost = new Post();
		newPost.setId(1L);
		newPost.setTitle("new post");
		newPost = postRepository.save(newPost);
		List<Post> foundedPosts = postRepository.findByTitleLikeIgnoreCase("New Post");
		assertTrue(foundedPosts.contains(newPost));
	}

	@Test
	@DisplayName("создает и получает запись")
	void createAndGetTest() {
		Post newPost = new Post();
		newPost.setId(1L);
		newPost.setTitle("new post");
		newPost = postRepository.save(newPost);
		assertNotNull(newPost.getId());
		assertFalse(newPost.getTitle().isEmpty());
		Post foundedPost = postRepository.findById(newPost.getId()).orElse(null);
		assertNotNull(foundedPost);
		assertEquals(newPost, foundedPost);
	}

	@Test
	@DisplayName("обновляет запись")
	void updateTest() {
		Post newPost = new Post();
		newPost.setId(1L);
		newPost.setTitle("new post");
		newPost = postRepository.save(newPost);
		assertTrue(Strings.isNullOrEmpty(newPost.getBody()));
		String body = "some text here";
		newPost.setBody(body);
		Post updatedPost = postRepository.save(newPost);
		assertEquals(body, updatedPost.getBody());
		assertEquals(newPost, updatedPost);
	}

	@Test
	@DisplayName("ищет записи пользователя")
	void findTest() {
		Post newPost = new Post();
		newPost.setId(1L);
		newPost.setTitle("new post");
		postRepository.save(newPost);
		List<Post> posts = postRepository.findAll();
		for (Post post : posts) {
			assertTrue(ImmutableSet.of(post).contains(post));
		}
	}

	@Test
	@DisplayName("удаляет записи")
	void deleteTest() {
		Post newPost = new Post();
		newPost.setId(1L);
		newPost.setTitle("new post");
		newPost = postRepository.save(newPost);
		Long id = newPost.getId();
		boolean isIdFound = false;
		List<Post> posts = postRepository.findAll();
		for (Post post : posts) {
			if (id.equals(post.getId())) {
				isIdFound = true;
				break;
			}
		}
		assertTrue(isIdFound);
		postRepository.deleteById(id);

		isIdFound = false;
		posts = postRepository.findAll();
		for (Post post : posts) {
			if (post.getId().equals(id)) {
				isIdFound = true;
				break;
			}
		}
		assertFalse(isIdFound);
	}

	@AfterEach
	public void cleanDatabase(){
		postRepository.deleteAll();
		userRepository.deleteAll();
	}
}
