package com.company.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import com.company.dao.BoardDAO;
import com.company.util.FileUtil;
import com.company.vo.BoardVO;


@MultipartConfig(
	location = "c:/temp",
	fileSizeThreshold = 512 * 1024,
	maxFileSize = 5 * 1024 * 1024,
	maxRequestSize = 10 * 1024 * 1024
)
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
		String title = request.getParameter("title");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String content = request.getParameter("content");
		
		if(title == null || title.isBlank() ||
		   nickname == null || nickname.isBlank() ||
		   password == null || password.isBlank() ||
		   content == null || content.isBlank()) {
			response.setContentType("text/html");
			response.getWriter().write("<script>alert('Failed to upload');history.back();</script>");
			return;
		}
		
		BoardVO vo = new BoardVO();
		vo.setTitle(title);
		vo.setNickname(nickname);
		vo.setPassword(password);
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
		
		boardDAO.createBoard(vo);
		response.sendRedirect("/home");
	}
}
