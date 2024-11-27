package com.company.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;

import com.company.dao.BoardDAO;

@WebServlet("/api/board/check-auth")
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BoardDAO boardDAO;
    {
    	boardDAO = new BoardDAO();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		
		int idx; String password;
		try {
			idx = Integer.parseInt(request.getParameter("idx"));
			password = request.getParameter("password");
		} catch (Exception e) {
			response.getWriter().print("{\"success\":\"false\"}");
			return;
		}
		
		if(password != null && boardDAO.checkPermission(idx, password)) {
			HttpSession session = request.getSession();
			HashSet<Integer> accessible = (HashSet<Integer>) session.getAttribute("accessible");
			if (accessible == null) {
			    accessible = new HashSet<>();
			    session.setAttribute("accessible", accessible);
			}
		    accessible.add(idx);
		    response.getWriter().print("{\"success\":\"true\"}");
			return;
		}
		response.getWriter().print("{\"success\":\"false\"}");
	}
}
