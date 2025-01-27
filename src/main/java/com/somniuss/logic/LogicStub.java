package com.somniuss.logic;

import com.somniuss.bean.AuthInfo;
import com.somniuss.bean.User;
import com.somniuss.dao.DaoException;
import com.somniuss.dao.impl.SQLUserDao;

public class LogicStub {
    
	public User checkAuth(AuthInfo authInfo) {
		
		SQLUserDao userDao = new SQLUserDao();
	    try {
	        return userDao.authorization(authInfo.getLogin(), authInfo.getPassword());
	    } catch (DaoException e) {
	        System.err.println("Ошибка при проверке аутентификации: " + e.getMessage());
	        return null;
	    }
	}

}

