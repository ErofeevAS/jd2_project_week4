package com.gmail.erofeev.st.alexei.fourthweek.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
        exclude = UserDetailsServiceAutoConfiguration.class,
        scanBasePackages = {
                "com.gmail.erofeev.st.alexei.fourthweek.service",
                "com.gmail.erofeev.st.alexei.fourthweek.repository",
                "com.gmail.erofeev.st.alexei.fourthweek"

        })
public class SpringBootModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootModuleApplication.class, args);
    }

}
