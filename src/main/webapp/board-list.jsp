<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board with images</title>
</head>
<body>
	<h1>Board with images</h1>
	<div>page size: 
		<form action="*">
			<select name="pagesize"> <!-- on change: update page size and reload -->
				<option value="10" selected>10</option>
				<option value="20">20</option>
				<option value="40">40</option>
				<option value="80">80</option>
			</select>
		</form>
	</div>
	<table>
	<thead>
		<tr>
			<td>Index</td>
			<td>Nickname</td>
			<td>Title</td>
			<td>Postdate</td>
			<td>Page View</td>
			<td>Download</td>
		<td>
		</tr>
	</thead>
	<c:forEach var="board" items="${boards }">
		<tr>
			<td>${board.idx }</td>
			<td>${board.nickname}</td>
			<td><a href="board?idx=${board.idx}">${board.title}</a></td>
			<td>${board.postdate}</td>
			<td>${board.pageview}</td>
			<td>${board.download}</td>
			<td>[download]</td>
		</tr>
	</c:forEach>
	</table>
	<div>1, 2, 3, 4, 5</div>
	<a href="#">Write new</a>
</body>
</html>