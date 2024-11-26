/**
 * 
 */
package com.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.company.vo.BoardVO;

import lombok.Setter;

/**
 * @author 04-14
 *
 */
public class BoardDAO {
	@Setter
	private int page_size = 10;
	
	DataSource dataSource;
	{
		dataSource = ConnectionPoolProvider.getDataSource();
	}
	
	public List<BoardVO> readBoardPage(int page){
		if(page < 1) page = 1;
		int startBoardOpen = page_size * (page - 1);
		int endBoardClosed = page_size * (page);
		List<BoardVO> boards = new ArrayList<>();
		String sql = "SELECT idx, nickname, title, postdate, pageview, download "
				+ "FROM board_view "
				+ "WHERE row_num > ? and row_num <= ?";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setInt(1, startBoardOpen);
			preparedStatement.setInt(2, endBoardClosed);
			try(ResultSet resultSet = preparedStatement.executeQuery(sql)){
				while(resultSet.next()) {
					BoardVO boardVO = new BoardVO();
					int idx = resultSet.getInt("idx");
					String nickname = resultSet.getString("nickname");
					String title = resultSet.getString("title");					
					Timestamp postdate = resultSet.getTimestamp("postdate");
					int pageview = resultSet.getInt("pageview");
					int download = resultSet.getInt("download");
					
					boardVO.setIdx(idx);
					boardVO.setNickname(nickname);
					boardVO.setTitle(title);
					boardVO.setPostdate(postdate);
					boardVO.setPageview(pageview);
					boardVO.setDownload(download);
					
					boards.add(boardVO);
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL error: " + e.getMessage());
		}
		
		
		return boards;
	}
	
	public BoardVO readBoard(int idx) {
		String sql = "SELECT nickname, title, content, "
					+ "org_img_path, real_img_path, "
					+ "postdate, pageview, download "
				+ "FROM board "
				+ "WHERE idx = ?";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setInt(1, idx);
			try(ResultSet resultSet = preparedStatement.executeQuery(sql)){
				if(resultSet.next()) {
					BoardVO boardVO = new BoardVO();
					
					String nickname = resultSet.getString("nickname");
					String title = resultSet.getString("title");					
					String content = resultSet.getString("content");					
					String originalImagePath = resultSet.getString("org_img_path");					
					String realImagePath = resultSet.getString("real_img_path");					
					
					Timestamp postdate = resultSet.getTimestamp("postdate");
					int pageview = resultSet.getInt("pageview");
					int download = resultSet.getInt("download");
					
					boardVO.setIdx(idx);
					boardVO.setNickname(nickname);
					boardVO.setTitle(title);
					boardVO.setContent(content);
					boardVO.setOriginalImagePath(originalImagePath);
					boardVO.setRealImagePath(realImagePath);
					
					boardVO.setPostdate(postdate);
					boardVO.setPageview(pageview);
					boardVO.setDownload(download);
					
					return boardVO;
				}
			}
		}catch (SQLException e) {
			System.err.println("SQL error: " + e.getMessage());
		}
		
		return null;
	}
}
