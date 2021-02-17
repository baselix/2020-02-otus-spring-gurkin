package ru.gurkin.spring.journal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gurkin.spring.journal.model.UserRole;
import ru.gurkin.spring.journal.security.model.AnonymousUserDetailsInfo;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JournalUserDetailService userDetailService;

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.ALWAYS )
                .and()
                .authorizeRequests().antMatchers( "/" ).permitAll()
                .and()
                .authorizeRequests().antMatchers( "/posts/**").authenticated()
                .and()
                .formLogin()
                .and()
                .anonymous().authorities(UserRole.ANONIMUS.name()).principal(new AnonymousUserDetailsInfo())
                .and().rememberMe()
        ;
        http.rememberMe()
                .key( "journalSecurityKey" )
                .tokenValiditySeconds( 50000 )
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userDetailService);
    }
}
