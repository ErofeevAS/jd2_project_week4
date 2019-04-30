package com.gmail.erofeev.st.alexei.fourthweek.service.connection.impl;

import com.gmail.erofeev.st.alexei.fourthweek.repository.DataBaseInitRepository;
import com.gmail.erofeev.st.alexei.fourthweek.service.SQLFileReaderService;
import com.gmail.erofeev.st.alexei.fourthweek.service.connection.ConnectionService;
import com.gmail.erofeev.st.alexei.fourthweek.service.connection.exception.DataBaseConnectionException;
import com.gmail.erofeev.st.alexei.fourthweek.service.exception.ServiceException;
import com.gmail.erofeev.st.alexei.fourthweek.service.properties.DataBaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class ConnectionServiceImpl implements ConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionServiceImpl.class);
    private final DataBaseProperties dataBaseProperties;
    private final SQLFileReaderService sqlFileReaderService;
    private final DataBaseInitRepository dataBaseInitRepository;

    @Autowired
    public ConnectionServiceImpl(DataBaseProperties databaseProperties, SQLFileReaderService sqlFileReaderService, DataBaseInitRepository dataBaseInitRepository) {
        this.sqlFileReaderService = sqlFileReaderService;
        this.dataBaseInitRepository = dataBaseInitRepository;
        try {
            Class.forName(databaseProperties.getDriver());
            this.dataBaseProperties = databaseProperties;
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new DataBaseConnectionException("jdbc driver not found: " + e.getMessage(), e);
        }
    }

    @PostConstruct
    private void createDataBaseTable() {
        logger.debug("initialization tables in the database... ");
        String fileName = dataBaseProperties.getInitDataBaseFileName();
        try (Connection connection = getConnection()) {
            File file = new ClassPathResource(fileName).getFile();
            String[] queries = sqlFileReaderService.getQueryFromFile(file);
            connection.setAutoCommit(false);
            try {
                dataBaseInitRepository.init(connection, queries);
                connection.commit();
                logger.debug("initialization tables in the database was successful ");
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(e.getMessage(), e);
            }
        } catch (SQLException e) {
            String message = "Connection to database can't be established";
            throw new ServiceException(message, e);
        } catch (IOException e) {
            String message = "Can't get file from this path: " + fileName;
            logger.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public Connection getConnection() {
        logger.debug("getting a connection to a database...");
        Properties properties = new Properties();
        String dataBaseUrl = dataBaseProperties.getUrl();
        properties.setProperty("user", dataBaseProperties.getUsername());
        properties.setProperty("password", dataBaseProperties.getPassword());
        try {
            Connection connection = DriverManager.getConnection(dataBaseUrl, properties);
            logger.debug("connection was established");
            return connection;
        } catch (SQLException e) {
            String message = "Can't connect to dataBase " + e.getMessage();
            logger.error(message);
            throw new DataBaseConnectionException(e.getMessage(), e);
        }
    }
}