package com.company.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.company.dao.BoardDAO;

@WebServlet("/board/create")
public class BoardCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BoardDAO boardDAO;
    {
    	boardDAO = new BoardDAO();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/board/board-create.html").forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO
		request.setCharacterEncoding("utf-8");
		response.sendRedirect("/board?idx=1");
		return;
	}
}
