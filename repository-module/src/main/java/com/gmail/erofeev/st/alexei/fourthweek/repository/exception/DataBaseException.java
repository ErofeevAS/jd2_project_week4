package com.gmail.erofeev.st.alexei.fourthweek.repository.exception;

public class DataBaseException extends RuntimeException {
    public DataBaseException(String s) {
    }

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseException(Throwable cause) {
        super(cause);
    }

    public DataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DataBaseException() {
    }

}
