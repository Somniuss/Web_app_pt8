package com.somniuss.service;

public class ServiceException extends Exception {

    // Конструктор без параметров
    public ServiceException() {
        super();
    }

    // Конструктор с параметром String
    public ServiceException(String message) {
        super(message); // Передаем сообщение в конструктор родительского класса Exception
    }

    // Конструктор с параметром Throwable (для случаев, когда нужно передать исключение)
    public ServiceException(Throwable cause) {
        super(cause);
    }

    // Конструктор с параметрами String и Throwable
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
