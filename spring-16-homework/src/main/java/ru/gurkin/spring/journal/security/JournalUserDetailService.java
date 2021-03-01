package ru.gurkin.spring.journal.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.service.JournalUserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class JournalUserDetailService implements UserDetailsService {

    private final JournalUserService userService;

    public JournalUserDetailService(JournalUserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login){
        JournalUser user = userService.getByLogin(login);
        if(user == null){
            throw new UsernameNotFoundException(String.format("User with login %s was not found", login));
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority userAuthority = new SimpleGrantedAuthority(user.getRole().name());
        authorities.add(userAuthority);
        return new User(user.getLogin(), user.getPassword(), authorities);
    }
}
