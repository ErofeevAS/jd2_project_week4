package com.gmail.erofeev.st.alexei.fourthweek.service.impl;

import com.gmail.erofeev.st.alexei.fourthweek.service.SQLFileReaderService;
import com.gmail.erofeev.st.alexei.fourthweek.service.exception.SQLFileReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Component
public class SQLFileReaderImpl implements SQLFileReaderService {
    private static final Logger logger = LoggerFactory.getLogger(SQLFileReaderImpl.class);

    @Override
    public String[] getQueryFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                while (line != null) {
                    if (line.length() > 0) {
                        stringBuilder.append(line);
                    }
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                String message = "File not exist: " + file + " : " + e.getMessage();
                logger.error(message);
                throw new SQLFileReaderException(message, e);
            } catch (IOException e) {
                String message = "Can't read file: " + file + " : " + e.getMessage();
                logger.error(message, e);
                throw new SQLFileReaderException(message, e);
            }
        }
        return stringBuilder.toString().split(";");
    }
}