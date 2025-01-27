package com.somniuss.dao;

public class DaoException extends Exception {
    
    // Конструктор без параметров
    public DaoException() {
        super();
    }

    // Конструктор с сообщением
    public DaoException(String message) {
        super(message);
    }

    // Конструктор с сообщением и причиной
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    // Конструктор с причиной
    public DaoException(Throwable cause) {
        super(cause);
    }
}
