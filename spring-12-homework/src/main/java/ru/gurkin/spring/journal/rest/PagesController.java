package ru.gurkin.spring.journal.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gurkin.spring.journal.model.Post;
import ru.gurkin.spring.journal.model.UserRole;
import ru.gurkin.spring.journal.service.PostService;

import java.util.List;
import java.util.Optional;

@Controller
public class PagesController {

    private final PostService postService;

    public PagesController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        User user = getUser();
        if (user != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(UserRole.ANONIMUS.name()))) {
            model.addAttribute("username", user.getUsername());
            return "index";
        }else{
            return "redirect:posts";
        }
    }

    @GetMapping("/posts")
    public String postsPage(Model model) {
        User user = getUser();
        List<Post> posts = postService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "posts";
    }

    @PostMapping("/posts")
    public String createPostsPage(Post post, Model model) {
        User user = getUser();
        postService.create(post);
        List<Post> posts = postService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String editPostPage(@PathVariable("id") String id, Model model) {
        Post post = Optional.ofNullable(postService.getById(id)).orElseThrow(NotFoundException::new);
        User user = getUser();
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "edit";
    }

    @PostMapping("/posts/{id}")
    public String updatePostPage(@PathVariable("id") String id, Post post, Model model) {
        User user = getUser();
        if(post.getId() == null){
            post = postService.create(post);
        }else if(id.equals(post.getId())){
            post = postService.update(post);
        }else{
            throw new IllegalArgumentException("path and post ids are different");
        }
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "edit";
    }

    private User getUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
