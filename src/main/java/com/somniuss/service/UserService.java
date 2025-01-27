package com.somniuss.service;

import com.somniuss.bean.User;

public interface UserService {
	
    // Метод для входа пользователя
    User signIn(String login, String password) throws ServiceException;

    // Метод для регистрации пользователя
    User registration(String name, String email, String password) throws ServiceException; // изменен
}
