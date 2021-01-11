package ru.gurkin.spring.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gurkin.spring.library.model.Genre;
import ru.gurkin.spring.library.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private GenreService service;

    @Autowired
    public GenreController(GenreService service){
        this.service = service;
    }

    @GetMapping
    public List<Genre> getAll(){
        return service.getAll();
    }

    @PostMapping
    public Genre createGenre(@RequestBody Genre genre){
        return service.create(genre);
    }

    @PutMapping("/{id}")
    public Genre updateGenre(@PathVariable String id, @RequestBody Genre genre){
        return service.update(genre);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Long id){
        service.delete(id);
    }
}
