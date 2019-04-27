package com.gmail.erofeev.st.alexei.fourthweek.repository;

import com.gmail.erofeev.st.alexei.fourthweek.repository.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserRepository {
    User findUserByName(Connection connection, String name);

    List<User> findAll(Connection connection, int offset, int amount);

    Integer getAmount(Connection connection);

    User add(Connection connection, User user);
}

