<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${board.title}</title>
    <link rel="stylesheet" href="/style/board-view.css">
</head>
<body>
<header>
    <a href="#" onclick="history.back();"
    	class="back-button">←</a>
    <span class="logo">LOGO</span>
    <span>menu</span>
</header>
<section>
    
    <c:if test="${not empty board.realImagePath}">
   		<img src="/assets/${board.realImagePath}" alt="image">
   		<div class="image-footer">
            <span>${board.download}</span>
            <button type="button">Download</button>
        </div>
   	</c:if>
       
    <div class="title">${board.title }</div>
    <div class="title-footer">
        <div class="metadata">
            Views: ${board.pageview }<br>
            Written by ${board.nickname}, ${board.postdate}<br>
        </div>
        <div class="crud">
            <span><a href="#" onclick="boardUpdate(${board.idx})">edit</a></span>
            <span><a href="#" onclick="boardDelete(${board.idx})">delete</a></span>
        </div>
        
    </div>

    <pre class="content">
${board.content}
    </pre>
</section>
<script>
function boardEdit(idx){
	return false;
}
</script>
</body>
</html>