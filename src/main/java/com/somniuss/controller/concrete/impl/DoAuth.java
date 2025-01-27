package com.somniuss.controller.concrete.impl;

import java.io.IOException;

import com.somniuss.bean.User;
import com.somniuss.controller.concrete.Command;
import com.somniuss.service.ServiceException;
import com.somniuss.service.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoAuth implements Command {
    private final UserService userService; // Интерфейс для внедрения зависимости

    // Конструктор для внедрения зависимости
    public DoAuth(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры логина и пароля
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        // Валидация логина
        if (login == null || login.isEmpty() || login.length() < 3 || login.length() > 50) {
            request.setAttribute("authError", "Логин должен быть от 3 до 50 символов!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            return; // Завершаем выполнение, если валидация не пройдена
        }

        // Валидация пароля
        if (password == null || password.isEmpty() || password.length() < 6 || password.length() > 20) {
            request.setAttribute("authError", "Пароль должен быть от 6 до 20 символов!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Регулярное выражение для проверки логина (только латинские буквы и цифры)
        if (!login.matches("[a-zA-Z0-9]+")) {
            request.setAttribute("authError", "Логин может содержать только буквы и цифры!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Логика аутентификации пользователя
        try {
            User user = userService.signIn(login, password); // Используем интерфейс

            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

                request.setAttribute("invitationMessage", "Hello, user!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("authError", "Неправильный логин или пароль!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ServiceException e) {
            e.printStackTrace(); // Логируем ошибку
            request.setAttribute("authError", "Ошибка при аутентификации пользователя. Пожалуйста, попробуйте позже.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}
