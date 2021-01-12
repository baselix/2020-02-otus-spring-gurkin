package ru.gurkin.spring.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gurkin.spring.library.model.Author;
import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private AuthorService service;

    @Autowired
    public AuthorController(AuthorService service){
        this.service = service;
    }

    @GetMapping
    public List<Author> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author){
        return service.create(author);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable String id, @RequestBody Author author){
        return service.update(author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuhtor(@PathVariable Long id){
        service.delete(id);
    }
}
