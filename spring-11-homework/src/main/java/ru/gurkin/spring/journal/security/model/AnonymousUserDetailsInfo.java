package ru.gurkin.spring.journal.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.gurkin.spring.journal.model.UserRole;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AnonymousUserDetailsInfo extends User {

    public AnonymousUserDetailsInfo(){
        super("anonymous", "pass", new HashSet<>());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = UserRole.ANONIMUS::name;
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);
        return authorities;
    }
}
