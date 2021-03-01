package ru.gurkin.spring.journal.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.gurkin.spring.journal.service.JournalUserService;

@Service
public class JournalUserDetailService implements ReactiveUserDetailsService {

    private final JournalUserService userService;

    public JournalUserDetailService(JournalUserService userService){
        this.userService = userService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String userName) {
        return userService.getByLogin(userName)
                .map(user -> User
                            .withUsername(user.getLogin())
                            .password(user.getPassword())
                            .roles(user.getRole().name())
                            .build());
    }
}
