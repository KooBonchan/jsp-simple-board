/**
 * 
 */
package com.company.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
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
	private static int page_size = 10;
	
	DataSource dataSource;
	{
		dataSource = ConnectionPoolProvider.getDataSource();
	}
	
	public boolean createBoard(BoardVO boardVO) {
		String sql = "INSERT INTO board "
				+ "(idx, nickname, password, title, content, org_img_path, real_img_path) "
				+ "VALUES "
				+ "(seq_board_idx.nextval, ?, ?, ?, ?, ?, ?) ";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setString(1, boardVO.getNickname());
			preparedStatement.setString(2, boardVO.getPassword());
			preparedStatement.setString(3, boardVO.getTitle());
			preparedStatement.setString(4, boardVO.getContent());
			
			String originalImagePath = boardVO.getOriginalImagePath();
			String realImagePath = boardVO.getRealImagePath();
			if(originalImagePath == null || realImagePath == null) {
				preparedStatement.setNull(5, Types.VARCHAR);
				preparedStatement.setNull(6, Types.VARCHAR);
			} else {
				preparedStatement.setString(5, originalImagePath);
				preparedStatement.setString(6, realImagePath);
			}
			
			int result = preparedStatement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println("SQL INSERT error: " + e.getMessage());
		}
		return false;
	}
	
	public List<BoardVO> readBoardPage(int page){
		return readBoardPage(page, null, null);
	}
	
	public List<BoardVO> readBoardPage(int page, String category, String searchQuery){
		if(page < 1) page = 1;
		int startBoardOpen = page_size * (page - 1);
		int endBoardClosed = page_size * (page);
		List<BoardVO> boards = new ArrayList<>();
		
		String sql = "SELECT idx, nickname, title, postdate, pageview, real_img_path, download "
				+ searchBoardInlineViewQuery(BoardSearchCategory.fromString(category), searchQuery)
				+ "WHERE row_num > ? and row_num <= ?";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setInt(1, startBoardOpen);
			preparedStatement.setInt(2, endBoardClosed);
			try(ResultSet resultSet = preparedStatement.executeQuery()){
				while(resultSet.next()) {
					BoardVO boardVO = new BoardVO();
					int idx = resultSet.getInt("idx");
					String nickname = resultSet.getString("nickname");
					String title = resultSet.getString("title");					
					Timestamp postdate = resultSet.getTimestamp("postdate");
					int pageview = resultSet.getInt("pageview");
					String realImagePath = resultSet.getString("real_img_path");
					int download = resultSet.getInt("download");
					
					boardVO.setIdx(idx);
					boardVO.setNickname(nickname);
					boardVO.setTitle(title);
					boardVO.setPostdate(postdate);
					boardVO.setPageview(pageview);
					if(realImagePath != null) {
						boardVO.setDownload(download);
					}
					else {
						boardVO.setDownload(-1);
					}
					
					boards.add(boardVO);
				}
			}
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println("SQL SELECT error: " + e.getMessage());
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
			try(ResultSet resultSet = preparedStatement.executeQuery()){
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
			System.err.println(sql);
			System.err.println("SQL error: " + e.getMessage());
		}
		
		return null;
	}
	
	public boolean checkPermission(int idx, String password) {
		String sql = "SELECT idx "
				+ "FROM board "
				+ "WHERE idx = ? and password = ? ";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setInt(1, idx);
			preparedStatement.setString(2, password.trim());
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if(resultSet.next()) return true;
			}
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println("SQL error: " + e.getMessage());
		}
		return false;
	}
	
	/*
	 * Check user permission before use
	 * */
	public boolean updateBoard(int idx, BoardVO boardVO) {
		// need permission
		boolean imageUpdated = boardVO.getOriginalImagePath() != null &&
								boardVO.getRealImagePath() != null;
		
		String sql = "UPDATE board SET "
				+ "title = ?, "
				+ "content = ? "
				+ (imageUpdated ? ",org_img_path = ?,real_img_path = ? " : "")
				+ "WHERE idx = ? ";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			int index = 1;
			preparedStatement.setString(index++, boardVO.getTitle());
			preparedStatement.setString(index++, boardVO.getContent());
			
			String originalImagePath = boardVO.getOriginalImagePath();
			String realImagePath = boardVO.getRealImagePath();
			if(imageUpdated) {
				preparedStatement.setString(index++, originalImagePath);
				preparedStatement.setString(index++, realImagePath);
			}
			preparedStatement.setInt(index++, boardVO.getIdx());
			
			int result = preparedStatement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println("SQL INSERT error: " + e.getMessage());
		}
		return false;
	}
	
	public boolean increasePageview(int idx) {
		String sql = "UPDATE board SET "
				+ "pageview = pageview + 1 "
				+ "WHERE idx = ? ";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setInt(1, idx);
			int result = preparedStatement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println("SQL INSERT error: " + e.getMessage());
		}
		return false;
	}
	
	public boolean increaseDownload(int idx) {
		String sql = "UPDATE board SET "
				+ "download = download + 1 "
				+ "WHERE idx = ? ";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setInt(1, idx);
			int result = preparedStatement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println("SQL INSERT error: " + e.getMessage());
		}
		return false;
	}
	
	/*
	 * Check user permission before use
	 * */
	public boolean deleteBoard(int idx) {
		// need permission
		String sql = "DELETE FROM board "
				+ "WHERE idx = ? ";
		try(Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setInt(1, idx);
			int result = preparedStatement.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			System.err.println(sql);
			System.err.println("SQL INSERT error: " + e.getMessage());
		}
		return false;
	}
	
	private String searchBoardInlineViewQuery(BoardSearchCategory category, String searchQuery) {
		if(category == null || searchQuery == null || searchQuery.length() == 0) {
			return " FROM board_view ";
		}
		if( ! searchQuery.matches("^[a-zA-Z0-9_ ]*$")) {
			return " FROM board_view ";
		}
		return " FROM ("
				+ " SELECT idx, nickname, title, postdate, pageview, org_img_path, download, "
				+ "    row_number() OVER (ORDER BY idx DESC) AS row_num "
				+ "  FROM board "
				+ "  WHERE "+ category.toString()+" LIKE '%"+searchQuery+"%'"
				+ ") target ";
	}
}