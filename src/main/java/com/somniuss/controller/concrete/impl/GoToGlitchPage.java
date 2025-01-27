package com.somniuss.controller.concrete.impl;

import java.io.IOException;

import com.somniuss.controller.concrete.Command;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToGlitchPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/glitch.jsp");
		dispatcher.forward(request, response);
		
	}

}
