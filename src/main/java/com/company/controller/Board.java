package com.company.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.company.dao.BoardDAO;
import com.company.vo.BoardVO;

@WebServlet("/board")
public class Board extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BoardDAO boardDAO;
    {
    	boardDAO = new BoardDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		int idx;
		try {
			idx = Integer.parseInt(request.getParameter("idx"));
		} catch (RuntimeException e) {
			response.sendRedirect("/home");
			return;
		}
		BoardVO board = boardDAO.readBoard(idx);
		if(board == null) {
			response.sendRedirect("/home");
			return;
		}
		request.setAttribute("board", board);
		request.getRequestDispatcher("/board/board-view.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
