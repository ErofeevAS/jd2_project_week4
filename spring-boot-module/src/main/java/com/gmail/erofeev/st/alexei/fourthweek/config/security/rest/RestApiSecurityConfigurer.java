package com.gmail.erofeev.st.alexei.fourthweek.config.security.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@Order(1)
public class RestApiSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("apiAccessDeniedHandler")
    private AccessDeniedHandler apiAccessDeniedHandler;

    @Autowired
    public RestApiSecurityConfigurer(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/v1/**")
                .authorizeRequests()
                .antMatchers("/api/v1/items")
                .hasRole("Customer")
                .antMatchers("/api/v1/users")
                .hasRole("Administrator")
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedHandler(apiAccessDeniedHandler)
                .and()
                .csrf()
                .disable();
    }
}
