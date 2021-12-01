package com.writerHub.practice.security;


import com.writerHub.practice.filter.AuthenticationFilter;
import com.writerHub.practice.filter.AuthorizationFilter;
import com.writerHub.practice.models.AuthenticationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authFilter = new AuthenticationFilter(authenticationManagerBean());
        authFilter.setFilterProcessesUrl("/auth/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        //http.authorizeRequests().antMatchers("/api/**").hasAnyAuthority("USER");
        http.authorizeRequests().anyRequest().permitAll();
        http.addFilter(authFilter);
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean @Override
    public  AuthenticationManager authenticationManagerBean() throws Exception {
        return  super.authenticationManagerBean();
    }
}
