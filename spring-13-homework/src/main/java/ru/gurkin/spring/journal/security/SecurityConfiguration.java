package ru.gurkin.spring.journal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gurkin.spring.journal.model.UserRole;
import ru.gurkin.spring.journal.security.model.AnonymousUserDetailsInfo;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JournalUserDetailService userDetailService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/springfox-swagger-ui/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/v2/api-docs")
                .antMatchers("/h2-console/**");
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.ALWAYS )
                .and()
                .authorizeRequests().antMatchers( "/" ).permitAll()
                .and()
                .authorizeRequests().antMatchers( "/posts/**" ).authenticated()
                .and()
                .authorizeRequests().antMatchers("/users/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .and()
                .anonymous().authorities(UserRole.ANONYMUS.name()).principal(new AnonymousUserDetailsInfo())
                .and().rememberMe()
        ;
        http.rememberMe()
                .key( "journalSecurityKey" )
                .tokenValiditySeconds( 60*60*24 )
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }

    @Override
    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userDetailService);
    }
}
