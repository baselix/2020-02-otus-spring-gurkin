package ru.gurkin.spring.library.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gurkin.spring.library.model.Book;
import ru.gurkin.spring.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService service;

    @Autowired
    public BookController(BookService service){
        this.service = service;
    }

    @GetMapping
    public List<Book> getAll(){
        return service.getAll();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return service.create(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable String id, @RequestBody Book book){
        return service.update(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        service.delete(id);
    }
}
