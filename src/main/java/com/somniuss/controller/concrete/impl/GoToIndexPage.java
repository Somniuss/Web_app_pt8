package com.somniuss.controller.concrete.impl;

import com.somniuss.bean.News;
import com.somniuss.controller.concrete.Command;
import com.somniuss.service.ServiceException;
import com.somniuss.service.impl.NewsServiceImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToIndexPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsServiceImpl newsService = new NewsServiceImpl();
        
        try {
            // Получаем новости
            List<News> newsList = newsService.getAllNews();
            
            // Устанавливаем новости в атрибут запроса
            request.setAttribute("news", newsList);
        } catch (ServiceException e) {
            // Логирование исключения (можно использовать логгер)
            e.printStackTrace();
            
            // Устанавливаем атрибут ошибки, чтобы показать сообщение на главной странице, но не прерываем выполнение
            request.setAttribute("errorMessage", "Ошибка при загрузке новостей. Попробуйте позже.");
            
            // Вместо перенаправления на страницу ошибки продолжаем выполнение и показываем главную страницу
        }
        
        // Перенаправляем на JSP-страницу независимо от ошибки
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main_index.jsp");
        dispatcher.forward(request, response);
    }
}
