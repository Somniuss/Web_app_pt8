package com.somniuss.service.impl;

import com.somniuss.bean.User;
import com.somniuss.dao.DaoException;
import com.somniuss.dao.DaoProvider;
import com.somniuss.dao.UserDao;
import com.somniuss.service.ServiceException;
import com.somniuss.service.UserService;

public class UserServiceImpl implements UserService {

	private final UserDao userDao = DaoProvider.getInstance().getUserDao();

	@Override
	public User signIn(String name, String password) throws ServiceException {
	    try {
	        User user = userDao.authorization(name, password);
	        return user;
	    } catch (DaoException e) {
	        throw new ServiceException("Ошибка при аутентификации пользователя", e);
	    }
	}



	@Override
	public User registration(String name, String email, String password) throws ServiceException {
	    try {
	        
	      
	        // Создаем нового пользователя
	        User user = new User(name, email, password);

	        // Добавляем пользователя в базу данных через UserDao
	        return userDao.registration(name, email, password);
	    } catch (DaoException e) {
	        throw new ServiceException("Ошибка при регистрации пользователя", e);
	    }
	}

	

}
