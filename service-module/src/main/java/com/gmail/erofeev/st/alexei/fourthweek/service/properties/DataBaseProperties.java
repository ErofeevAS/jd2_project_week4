package com.gmail.erofeev.st.alexei.fourthweek.service.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataBaseProperties {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username:}")
    private String username;
    @Value("${spring.datasource.password:}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${my.database.init.file}")
    private String initDataBaseFileName;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public String getInitDataBaseFileName() {
        return initDataBaseFileName;
    }
}