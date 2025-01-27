package com.somniuss.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.somniuss.controller.concrete.Command;
import com.somniuss.controller.concrete.CommandProvider;
import com.somniuss.service.UserService;
import com.somniuss.service.impl.UserServiceImpl; 

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CommandProvider provider;

    public Controller() {
        super();

        // Создаем экземпляр UserService
        UserService userService = new UserServiceImpl();

        // Передаем UserService в CommandProvider
        provider = new CommandProvider(userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userCommand = request.getParameter("command");
        Command command = provider.takeCommand(userCommand);
        command.execute(request, response);
    }
}
