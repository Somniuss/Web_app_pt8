package com.somniuss.bean;

import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String password; // Исходный пароль (не хранится в базе)
    private String cachedPasswordHash; // Хэш пароля

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        setPassword(password);
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        setPassword(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.cachedPasswordHash = hashPassword(password);
    }

    public String getCachedPasswordHash() {
        return cachedPasswordHash;
    }

    private String hashPassword(String password) {
        // Использование BCrypt для хэширования пароля
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(cachedPasswordHash, user.cachedPasswordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, cachedPasswordHash);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
