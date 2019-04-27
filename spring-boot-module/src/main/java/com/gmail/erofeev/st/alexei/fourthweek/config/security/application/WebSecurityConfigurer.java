package com.gmail.erofeev.st.alexei.fourthweek.config.security.application;

import com.gmail.erofeev.st.alexei.fourthweek.config.security.application.handler.AppAuthenticationSuccessHandler;
import com.gmail.erofeev.st.alexei.fourthweek.config.security.application.handler.LoginAccessDeniedHandler;
import com.gmail.erofeev.st.alexei.fourthweek.config.properties.SecurityProperties;
import com.gmail.erofeev.st.alexei.fourthweek.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final SecurityProperties securityProperties;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfigurer(UserDetailsService userDetailsService, SecurityProperties securityProperties, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/items")
                .access("hasRole('Customer')")
                .antMatchers("/users")
                .access("hasRole('Administrator')")
                .antMatchers("/", "/403", "/about", "/login")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppAuthenticationSuccessHandler(securityProperties);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new LoginAccessDeniedHandler();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
}
