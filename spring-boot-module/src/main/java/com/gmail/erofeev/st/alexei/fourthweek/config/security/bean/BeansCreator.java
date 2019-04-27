package com.gmail.erofeev.st.alexei.fourthweek.config.security.bean;

import com.gmail.erofeev.st.alexei.fourthweek.config.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansCreator {

    @Autowired
    private final SecurityProperties securityProperties;

    public BeansCreator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(securityProperties.getBcryptRounds());
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
