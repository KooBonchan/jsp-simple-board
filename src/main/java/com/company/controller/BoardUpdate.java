package com.company.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.company.dao.BoardDAO;
import com.company.vo.BoardVO;

@WebServlet("/board/update")
public class BoardUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BoardDAO boardDAO;
    {
    	boardDAO = new BoardDAO();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	int idx;
    	try {
    		idx = Integer.parseInt(request.getParameter("idx"));
    	} catch(NumberFormatException e) {
    		response.sendRedirect("/home?error='No Document'");
    		return;
    	}
    	
    	BoardVO board = boardDAO.readBoard(idx); 
    	if(board != null) {
    		response.sendRedirect("/home?error='No Document'");
    		return;
    	}
    	request.setAttribute("board", board);
		request.getRequestDispatcher("/board/board-update.jsp").forward(request, response);
		
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO
		request.setCharacterEncoding("utf-8");
		response.sendRedirect("/board?idx=1");
		return;
	}

}
