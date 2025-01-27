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

public class DoRegistration implements Command {
    private final UserService userService;

    // Конструктор для внедрения зависимости
    public DoRegistration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получение данных из формы
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Проверка на пустые поля
        if (name == null || email == null || password == null || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Все поля должны быть заполнены.");
            forwardToRegistrationPage(request, response);
            return;
        }

        // Проверка длины имени и пароля
        if (name.length() < 3 || name.length() > 50) {
            request.setAttribute("error", "Имя должно содержать от 3 до 50 символов.");
            forwardToRegistrationPage(request, response);
            return;
        }

        if (password.length() < 6 || password.length() > 20) {
            request.setAttribute("error", "Пароль должен содержать от 6 до 20 символов.");
            forwardToRegistrationPage(request, response);
            return;
        }

        // Регистрация пользователя через UserService
        try {
            User newUser = userService.registration(name, email, password);
            if (newUser != null) {
                // Если регистрация успешна, перенаправляем на страницу входа
                response.sendRedirect("MyController?command=go_to_index_page");
            } else {
                request.setAttribute("error", "Ошибка регистрации. Попробуйте снова.");
                forwardToRegistrationPage(request, response);
            }
        } catch (ServiceException e) {
            e.printStackTrace(); // Логируем ошибку
            request.setAttribute("error", "Ошибка при регистрации пользователя. Попробуйте позже.");
            forwardToRegistrationPage(request, response);
        }
    }

    private void forwardToRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/registration.jsp");
        dispatcher.forward(request, response);
    }
}
