package com.somniuss.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.somniuss.bean.User;
import com.somniuss.dao.DaoException;
import com.somniuss.dao.UserDao;
import com.somniuss.dao.connectionpoolprovider.ConnectionPoolProvider;
import com.somniuss.dao.сonnectionpool.ConnectionPoolException;

public class SQLUserDao implements UserDao {

	private static final String REGISTRATION = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

	@Override
	public User registration(String name, String email, String password) throws DaoException {
		String hashedPassword = hashPassword(password);

		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement ps = con.prepareStatement(REGISTRATION)) {
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, hashedPassword);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				return new User(name, email, hashedPassword);
			} else {
				throw new DaoException("Ошибка при добавлении пользователя");
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Ошибка при регистрации пользователя", e);
		}
	}

	private static final String IsUserExistByName = "SELECT 1 FROM users WHERE name = ?";

	@Override
	public boolean isUserExistsByName(String name) throws DaoException {
		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement ps = con.prepareStatement(IsUserExistByName)) {
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Ошибка проверки существования пользователя по имени", e);
		}
	}

	private static final String IsUserExistsByEmail = "SELECT 1 FROM users WHERE email = ?";

	@Override
	public boolean isUserExistsByEmail(String email) throws DaoException {

		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement ps = con.prepareStatement(IsUserExistsByEmail)) {
			ps.setString(1, email);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Ошибка проверки существования пользователя по email", e);
		}
	}

	private static final String Authorisation = "SELECT * FROM users WHERE name = ?";

	@Override
	public User authorization(String name, String password) throws DaoException {

		try (Connection con = ConnectionPoolProvider.getConnectionPool().takeConnection();
				PreparedStatement ps = con.prepareStatement(Authorisation)) {

			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String hashedPassword = rs.getString("password");
					if (checkPassword(password, hashedPassword)) {
						return new User(rs.getString("name"), rs.getString("email"), hashedPassword);
					}
				}
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Ошибка при авторизации пользователя", e);
		}

		return null;
	}

	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	private boolean checkPassword(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}
}
