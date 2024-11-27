package com.company.controller.filter;

import java.io.IOException;
import java.util.HashSet;

import com.company.dao.BoardDAO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
		"/board/update",
		"/board/delete",
})
public class AuthFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
    BoardDAO boardDAO;
    {
    	boardDAO = new BoardDAO();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
    	
        int idx;
    	try {
    		idx = Integer.parseInt(request.getParameter("idx"));
    	} catch (NumberFormatException e) {
    		System.out.println("Invalid access: wrong idx");
    		System.out.println(e.getMessage());
    		historyBack(httpRequest, httpResponse);
    		return;
    	}
    	
    	HttpSession session = httpRequest.getSession(false);
    	if( session == null ) {
    		System.out.println("Invalid access: session invalidated");
    		historyBack(httpRequest, httpResponse);
    		return;
    	}
    	
    	@SuppressWarnings("unchecked")
		HashSet<Integer> accessible = (HashSet<Integer>) session.getAttribute("accessible");
    	if( accessible == null || !accessible.contains(idx)) {
    		System.out.println("Invalid access: no auth for this page");
    		historyBack(httpRequest, httpResponse);
    		return;
    	}
    	chain.doFilter(request, response);
	}
    
    private void historyBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String referer = request.getHeader("Referer");
    	if(referer == null || referer.isEmpty()) {
    		referer = "/home";
    	}
    	response.sendRedirect(referer);
    }

}
