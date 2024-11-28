package com.company.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

import org.json.JSONObject;

import com.company.dao.BoardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/board/check-permission")
public class CheckPermission extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BoardDAO boardDAO;
    {
    	boardDAO = new BoardDAO();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		
		int idx; String password;
		try (BufferedReader reader = new BufferedReader(request.getReader())){
			StringBuilder jsonString = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
	        JSONObject json = new JSONObject(jsonString.toString());
			idx = json.getInt("idx");
			password = json.getString("password");
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	        return;
		}
		
		if(password != null && boardDAO.checkPermission(idx, password)) {
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			HashSet<Integer> accessible = (HashSet<Integer>) session.getAttribute("accessible");
			if (accessible == null) {
			    accessible = new HashSet<>();
			    session.setAttribute("accessible", accessible);
			}
		    accessible.add(idx);
		    response.setStatus(HttpServletResponse.SC_OK); // 200 OK
	        return;
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
	}
}
