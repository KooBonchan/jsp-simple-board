package com.company.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import com.company.dao.BoardDAO;
import com.company.util.FileUtil;
import com.company.vo.BoardVO;

@MultipartConfig(
		location = "c:/temp",
	fileSizeThreshold = 512 * 1024,
	maxFileSize = 5 * 1024 * 1024,
	maxRequestSize = 10 * 1024 * 1024
)
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
    		response.sendRedirect("/home?error='Wrong idx format'");
    		return;
    	}
    	BoardVO board = boardDAO.readBoard(idx); 
    	if(board == null) {
    		response.sendRedirect("/home?error='No Document'");
    		return;
    	}
    	request.setAttribute("board", board);
		request.getRequestDispatcher("/board/board-update.jsp").forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO
		request.setCharacterEncoding("utf-8");
		int idx;
    	try {
    		idx = Integer.parseInt(request.getParameter("idx"));
    	} catch(NumberFormatException e) {
    		response.sendRedirect("/home?error='Wrong idx format'");
    		return;
    	}
    	String title = request.getParameter("title");
		String nickname = request.getParameter("nickname");
		String content = request.getParameter("content");
		if(title == null || title.isBlank() ||
		   nickname == null || nickname.isBlank() ||
		
		   content == null || content.isBlank()) {
			response.setContentType("text/html");
			response.getWriter().write("<script>alert('Failed to upload');history.back();</script>");
			return;
		}
		
    	BoardVO vo = new BoardVO();
    	vo.setIdx(idx);
    	vo.setTitle(title);
    	vo.setNickname(nickname);
    	vo.setContent(content);
    	Part part = request.getPart("image");
		if(part != null && part.getSize() != 0) {
			String saveDirectory = getServletContext().getRealPath("/uploads");
			String original = FileUtil.extractFileName(part);
			String real = FileUtil.generateFileName(original);
			try {
				part.write(saveDirectory + File.separator + real);
				vo.setOriginalImagePath(original);
				vo.setRealImagePath(real);
			}catch (IOException e) {
				System.out.println("file save error");
			}
		}
    	
    	boardDAO.updateBoard(idx, vo);
    	HttpSession session = request.getSession();
    	if(session != null) {
    		@SuppressWarnings("unchecked")
    		HashSet<Integer> accessible = (HashSet<Integer>) session.getAttribute("accessible");
        	if( accessible != null & accessible.contains(idx)) {
        		accessible.remove(idx);
        	}

    	}    	
    	
    	response.sendRedirect("/board?idx=" + idx);
		return;
	}

}
