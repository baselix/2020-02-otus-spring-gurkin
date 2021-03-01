package ru.gurkin.spring.journal.rest;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.model.Post;
import ru.gurkin.spring.journal.service.PostService;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public Flux<Post> getPosts() {
        return postService.getAll();
    }

    @GetMapping("/posts/{id}")
    public Mono<Post> getPost(@PathVariable("id") String id) {
        return postService.getById(id).cache();
    }


    @PostMapping("/posts")
    public Mono<Post> createPost(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping("/posts/{id}")
    public Mono<Post> updatePost(@RequestBody Post post) {
        return postService.update(post);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable("id") String id){
        postService.delete(id);
    }

}