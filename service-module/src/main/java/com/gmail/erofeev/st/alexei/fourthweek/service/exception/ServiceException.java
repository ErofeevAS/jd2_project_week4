package com.gmail.erofeev.st.alexei.fourthweek.service.exception;

import java.sql.SQLException;

public class ServiceException extends RuntimeException {
    public ServiceException(String message, SQLException e) {
    }
}
