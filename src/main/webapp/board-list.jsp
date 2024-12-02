<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board with images</title>
<link rel="stylesheet" href="/style/board-list.css">
</head>
<body>
	<h1><a href="/home">Board with images</a></h1>
	<form id="form-search">
		<select name="search_category">
			<option value="title" selected>Title</option>
			<option value="nickname">Writer</option>
			<option value="content">Content</option>
		</select>
		<input type="text" name="search_query"/>
		<input type="submit" value="search"/>
		<button type="button" onclick="location.href = location.origin+location.pathname">reset</button>
	</form>
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
			<td class="index">${board.idx }</td>
			<td class="writer">${board.nickname}</td>
			<td class="title"><a href="board?idx=${board.idx}" class="title-link">${board.title}</a></td>
			<td class="postdate">
			<fmt:formatDate value="${board.postdate}"
			type="date" dateStyle="short"/>
			
			</td>
			<td>${board.pageview}</td>
			<c:choose>
			<c:when test="${board.download >= 0}">
				<td>${board.download}</td>
				<td><a href="/board/download?idx=${board.idx}" class="download">[download]</a></td>
			</c:when>
			<c:otherwise>
				<td>-</td>
				<td>no image</td>
			</c:otherwise>
			</c:choose>
		</tr>
	</c:forEach>
	</table>


	<footer>
	<div id="page-index">
		<c:set var="page" value="${empty param.page? 1 : param.page}" />
		<c:url var="baseUrl" value="/home">
		    <c:param name="search_category" value="${param.search_category}" />
		    <c:param name="search_query" value="${param.search_query}" />
		</c:url>
		<a href="${baseUrl}">base</a>
	    <c:choose>
            <c:when test="${page - 5 <= 0}">
               <span>prev</span>
            </c:when>
			<c:otherwise>
				<c:url var="prevUrl" value="${baseUrl}">
					<c:param name="page" value="${page - 5}" />
				</c:url>
				<a href="${prevUrl}">prev</a>
			</c:otherwise>
		</c:choose>
	    
		<c:forEach var="pages" begin="${page - (page-1) % 5}" end="${page - (page-1)%5 + 4}" >
			<c:choose>
            <c:when test="${pages == page}">
                <span>${pages}</span>
            </c:when>
            <c:otherwise>
                <c:url var="pageUrl" value="${baseUrl}">
	                <c:param name="page" value="${pages}" />
	            </c:url>
	            <a href="${pageUrl}">${pages}</a>
            </c:otherwise>
        	</c:choose>
		</c:forEach>
		<c:url var="nextUrl" value="${baseUrl}">
		    <c:param name="page" value="${page + 5}" />
		</c:url>
		<a href="${nextUrl}">next</a>
		
		<label for="page-size">Page Size: </label>
		<c:set var="pageSize" value="${sessionScope.pageSize != null ? sessionScope.pageSize : 10}" />
		<select id="page-size" onchange="updatePageSize()">
	        <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
	        <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
	        <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
	    </select>
	</div>
	<a href="./board/create" class="write-new">Write new</a>
	</footer>	
</body>

<script>
    function updatePageSize() {
        const pageSize = document.getElementById('page-size').value;

        fetch('/api/pagesize', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ pageSize: pageSize }),
        })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                console.error('Failed to update page size');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    
    
</script>

</html>