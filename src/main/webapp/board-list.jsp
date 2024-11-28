<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board with images</title>
</head>
<body>
	<h1>Board with images</h1>
	<form>
		<select name="search-category">
			<option value="title" selected>Title</option>
			<option value="nickname">Writer</option>
			<option value="content">Content</option>
		</select>
		<input type="text" name="search-query"/>
		<input type="submit" value="search"/>
	</form>
	<button type="button" onclick="window.location.href = window.location.origin+window.location.pathname">search reset</button>
	<table>
	<thead>
		<tr>
			<td>Index</td>
			<td>Writer</td>
			<td>Title</td>
			<td>Postdate</td>
			<td>View</td>
			<td>Download</td>
		<td>
		</tr>
	</thead>
	<c:forEach var="board" items="${boards }">
		<tr>
			<td>${board.idx }</td>
			<td>${board.nickname}</td>
			<td><a href="board?idx=${board.idx}">${board.title}</a></td>
			<td>
			<fmt:formatDate value="${board.postdate}"
			type="date" dateStyle="short"/>
			
			</td>
			<td>${board.pageview}</td>
			<c:choose>
			<c:when test="${not empty board.realImagePath}">
			<td>${board.download}</td>
			<td><a href="#">[download]</a></td>
			</c:when>
			<c:otherwise>
			<td>-</td>
			<td>no image</td>
			</c:otherwise>
			</c:choose>
		</tr>
	</c:forEach>
	</table>
	<div>1, 2, 3, 4, 5</div>
	<a href="./board/create">Write new</a>
</body>
</html>