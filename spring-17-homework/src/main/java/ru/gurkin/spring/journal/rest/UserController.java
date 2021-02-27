package ru.gurkin.spring.journal.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.service.JournalUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final JournalUserService userService;

    public UserController(JournalUserService userService){
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<JournalUser> getUsers(JournalUser user){
        return userService.getAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public JournalUser createUser(JournalUser user){
        return userService.create(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
    }
}
