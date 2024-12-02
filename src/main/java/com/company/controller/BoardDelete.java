package com.company.controller;

import java.io.IOException;

import com.company.dao.BoardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/delete")
public class BoardDelete extends HttpServlet {
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
    		response.sendRedirect("/home?error='Wrong idx format'");
    		return;
    	}
    	
    	if( ! boardDAO.deleteBoard(idx)) {
    		response.getWriter().print(""
					+ "<script>alert('Wrong input for file download.'); history.back();</script>");
    		return;
    	}
    	response.sendRedirect("/home");
		
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO
		request.setCharacterEncoding("utf-8");
		response.sendRedirect("/board?idx=1");
		return;
	}

}
