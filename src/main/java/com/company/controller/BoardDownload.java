package com.company.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.company.dao.BoardDAO;
import com.company.vo.BoardVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/download")
public class BoardDownload extends HttpServlet {
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
			if(idx <= 0) {
				throw new NumberFormatException("idx should be positive");
			}
			BoardVO vo = boardDAO.readBoard(idx);
			if( vo != null && vo.getRealImagePath() != null && !vo.getRealImagePath().isEmpty()) {
				String saveDirectory = getServletContext().getRealPath("/uploads");
				File file = new File(saveDirectory, vo.getRealImagePath());
				if (!file.exists() || !file.isFile()) {
				    throw new FileNotFoundException("The file " + file.getAbsolutePath() + " was not found or is not a valid file.");
				}
				response.reset();
				response.setContentType("application/octet-stream");
				response.setHeader("content-disposition", "attachment; filename=\""+vo.getOriginalImagePath()+"\"");
				response.setHeader("content-length", "" + file.length());
				
				try(InputStream inputStream = new FileInputStream(file);
					OutputStream outputStream = response.getOutputStream();)
				{
					byte b[] = new byte[(int)file.length()];
					int readBuffer = 0;
					while((readBuffer = inputStream.read(b)) > 0) {
						outputStream.write(b, 0, readBuffer);
					}
				}
			}
		} catch (NumberFormatException e) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(""
					+ "<script>alert('Wrong input for file download.'); history.back();</script>");
	        return;
		} catch(FileNotFoundException e) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(""
					+ "<script>alert('Cannot find the file.'); history.back();</script>");
		} catch(Exception e) {
			System.err.println(e.getMessage());
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(""
					+ "<script>alert('Unexpected Server Error.'); history.back();</script>");
		}
	}
}
