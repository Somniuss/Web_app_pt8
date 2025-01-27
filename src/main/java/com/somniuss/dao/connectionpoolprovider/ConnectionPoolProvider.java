package com.somniuss.dao.connectionpoolprovider;

import com.somniuss.dao.сonnectionpool.ConnectionPool;
import com.somniuss.dao.сonnectionpool.ConnectionPoolException;

public class ConnectionPoolProvider {
    private static final ConnectionPool connectionPool;

    static {
        try {
            // Явная регистрация драйвера MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Создание пула соединений
            connectionPool = new ConnectionPool(10,
                "jdbc:mysql://localhost:3306/soundeffects_management_v1?serverTimezone=Europe/Minsk",
                "root",
                "root"
                
            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC-драйвер MySQL не найден", e);
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Ошибка при создании ConnectionPool", e);
        }
    }

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
