package com.gmail.erofeev.st.alexei.fourthweek.repository;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Role;

import java.sql.Connection;

public interface RoleRepository {
    Role findByName(Connection connection, String name);
}
