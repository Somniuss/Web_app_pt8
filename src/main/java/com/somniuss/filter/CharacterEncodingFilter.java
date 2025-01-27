package com.somniuss.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {

    private String encoding;

    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null) {
            encoding = "UTF-8"; // Значение по умолчанию
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Устанавливаем кодировку запроса
        request.setCharacterEncoding(encoding);
        // Устанавливаем кодировку ответа
        response.setContentType("text/html; charset=" + encoding);
        response.setCharacterEncoding(encoding);
        
        // Продолжаем обработку запроса
        chain.doFilter(request, response);
    }

    public void destroy() {
        // Очистка ресурсов, если нужно
    }
}
