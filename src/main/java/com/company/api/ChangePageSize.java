package com.company.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

import com.company.dao.BoardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/pagesize")
public class ChangePageSize extends HttpServlet {
	private static final long serialVersionUID = 1L;
    BoardDAO boardDAO;
    {
    	boardDAO = new BoardDAO();
    }

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		
		StringBuilder body = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }
        try {
            JSONObject jsonRequest = new JSONObject(body.toString());
            int pageSize = jsonRequest.getInt("pageSize");
            HttpSession session = request.getSession();
            session.setAttribute("pageSize", pageSize);
            BoardDAO.setPage_size(pageSize);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        
	}
}
