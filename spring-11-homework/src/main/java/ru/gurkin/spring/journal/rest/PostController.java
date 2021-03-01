package ru.gurkin.spring.journal.rest;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.Post;
import ru.gurkin.spring.journal.service.PostService;

import java.util.Objects;

@RestController
public class PostController {

    private final Mono<SecurityContext> context  = ReactiveSecurityContextHolder.getContext();

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public Flux<Post> getPosts() {
        return postService.getAll(getUserName(context));
    }

    @GetMapping("/posts/{id}")
    public Mono<Post> getPost(@PathVariable("id") String id) {
        return postService.getById(id, getUserName(context)).cache();
    }


    @PostMapping("/posts")
    public Mono<Post> createPost(@RequestBody Post post) {
        return postService.create(post, getUserName(context));
    }

    @PutMapping("/posts/{id}")
    public Mono<Post> updatePost(@RequestBody Post post) {
        return postService.update(post, getUserName(context));
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable("id") String id){
        postService.delete(id);
    }

    private Mono<String> getUserName(Mono<SecurityContext> context){
		return context.filter(c -> Objects.nonNull(c.getAuthentication()))
				.map(c -> c.getAuthentication())
				.filter(a -> Objects.nonNull(a.getPrincipal()))
				.map(a -> a.getPrincipal())
				.filter(p -> Objects.nonNull(p))
				.map(p -> (User)p)
                .map(user -> user.getUsername());

	}
}