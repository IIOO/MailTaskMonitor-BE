package com.monitor.task.config.security;

import com.google.gson.Gson;
import com.monitor.task.security.BasicAuthenticationEntryPoint;
import com.monitor.task.security.filters.BasicTokenAuthenticationFilter;
import com.monitor.task.security.filters.TokenAuthenticationFilter;
import com.monitor.task.security.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final Gson gson;

    private final AuthTokenService tokenService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        //TODO enable csrf
        http
            .csrf().disable()
                .httpBasic()
                .authenticationEntryPoint(new BasicAuthenticationEntryPoint(gson))
                .and()
                .authorizeRequests().anyRequest().authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        final BasicTokenAuthenticationFilter authenticationFilter = new BasicTokenAuthenticationFilter(this.authenticationManagerBean(), tokenService);
        final TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(tokenService);

        http.addFilter(authenticationFilter);
        http.addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class);
    }

    @Configuration
    public static class Encoders {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}

