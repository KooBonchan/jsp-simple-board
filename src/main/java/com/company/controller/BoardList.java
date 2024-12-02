package com.company.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.company.dao.BoardDAO;

@WebServlet("/home")
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BoardDAO boardDAO;
    {
    	boardDAO = new BoardDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("page"));
			if(page < 0) throw new NumberFormatException("invalid page range");
		} catch(RuntimeException ignored) {
			
		}
		
		String searchCategory = request.getParameter("search-category");
		String searchQuery = request.getParameter("search-query");
		request.setAttribute("boards", boardDAO.readBoardPage(page, searchCategory, searchQuery));
		request.getRequestDispatcher("board-list.jsp").forward(request, response);
	}

}
