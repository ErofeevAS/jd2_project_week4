package com.gmail.erofeev.st.alexei.fourthweek.repository.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.DataBaseInitRepository;
import com.gmail.erofeev.st.alexei.fourthweek.repository.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DataBaseInitRepositoryImpl implements DataBaseInitRepository {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseInitRepositoryImpl.class);

    @Override
    public void init(Connection connection, String[] queries) {
        try (Statement statement = connection.createStatement()) {
            for (String query : queries) {
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            String message = "Can't create database layer repo: " + e.getMessage();
            logger.error(message);
            throw new DataBaseException(message, e);
        }
    }
}