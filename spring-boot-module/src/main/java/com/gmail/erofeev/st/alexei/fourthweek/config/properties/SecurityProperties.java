package com.gmail.erofeev.st.alexei.fourthweek.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityProperties {

    @Value("${app.security.role.user}")
    private String roleUser;
    @Value("${app.security.role.admin}")
    private String roleAdmin;
    @Value("${app.security.bcrypt.strength}")
    private String bcryptRounds;

    public String getRoleUser() {
        return roleUser;
    }

    public String getRoleAdmin() {
        return roleAdmin;
    }

    public int getBcryptRounds() {
        return Integer.parseInt(bcryptRounds);
    }
}


