package com.somniuss.bean;

import java.io.Serializable;
import java.util.Objects;

public class AuthInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	
	public AuthInfo() {

	}

	public AuthInfo(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(login, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthInfo other = (AuthInfo) obj;
		return Objects.equals(login, other.login) && Objects.equals(password, other.password);
	}

}
