package ru.gurkin.spring.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gurkin.spring.library.model.Comment;
import ru.gurkin.spring.library.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService service;

    @Autowired
    public CommentController(CommentService service){
        this.service = service;
    }

    @GetMapping
    public List<Comment> getAll(){
        return service.getAllComments();
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment){
        return service.create(comment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable String id, @RequestBody Comment comment){
        return service.update(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        service.delete(id);
    }
}
