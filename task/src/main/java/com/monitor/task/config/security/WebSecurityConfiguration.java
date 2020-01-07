package com.monitor.task.config.security;

import com.google.gson.Gson;
import com.monitor.task.security.BasicAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    private final Gson gson;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        //TODO enable csrf
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/user/**").permitAll()
            .antMatchers("/tasks/**").hasAnyAuthority(ADMIN, USER)
                .and()
                .httpBasic().authenticationEntryPoint(new BasicAuthenticationEntryPoint(gson));
    }

    @Configuration
    public static class Encoders {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}

