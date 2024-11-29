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
   		<img src="/uploads/${board.realImagePath}" alt="image">
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
            <span><a href="#" onclick="boardUpdate();">edit</a></span>
            <span><a href="#" onclick="boardDelete();">delete</a></span>
        </div>
        
    </div>

    <pre class="content">
${board.content}
    </pre>
</section>
<dialog id="password-dialog">
	<form method="dialog">
		<h3>Enter Document Password</h3>
		<input type="password" id="password-input" placeholder="Password"
			required />
		<button type="submit">Submit</button>
		<button type="button" id="cancel-button">Cancel</button>
	</form>
</dialog>
<script>
const idx = new URLSearchParams(window.location.search).get('idx');

const passwordDialog = document.getElementById('password-dialog');
const passwordInput = document.getElementById('password-input');
const cancelButton = document.getElementById('cancel-button');

let targetPage = '#';
function boardUpdate(){
	targetPage = '/board/update';
	passwordDialog.showModal();
}
function boardDelete(){
	targetPage = '/board/delete';
	passwordDialog.showModal();
}
/******** Dialogs ********/
cancelButton.addEventListener('click', ()=>passwordDialog.close());
passwordDialog.querySelector('form').addEventListener('submit', (event) => {
    event.preventDefault();
    const password = passwordInput.value;
    
    fetch('/api/board/check-permission', {
    	method: 'POST',
    	headers: {
    		'Content-Type': 'application/json'
    	},
    	body: JSON.stringify({
    		'idx': idx,
    		'password': password
    	})
    })
    .then((response) => {
    	if(response.ok) {
    		window.location.href = targetPage + "?idx=" + idx;
    	} else {
            alert('Invalid password. Please try again.');
        }
    })
    .catch((error) => {
      	alert('Failed to authenticate. Try again.');
     });
    
    passwordInput.value = '';
    passwordDialog.close();
});
</script>
</body>
</html>