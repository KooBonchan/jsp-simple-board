<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="/style/board-create.css">
  </head>
  <body>
    <header>
      <a href="#" onclick="history.back();"
        class="back-button">←</a>
      <span class="logo">LOGO</span>
      <span>menu</span>
    </header>
    <section>
      <form
        action="#"
        method="post"
        name="form-write-board"
        enctype="multipart/form-data"
      >
        <ul>
          <li>
            <input
              type="text"
              name="title"
              id="title"
              placeholder="Add Title"
              value="${board.title }"
              required
            />
          </li>
          <li>
            <div>
              <input
              type="text"
              name="nickname"
              id="nickname"
              placeholder="Writer Name"
              value="${board.nickname }"
              required
            />
            <input
              type="password"
              name="password"
              id="password"
              placeholder="Password"
              disabled
            />
            </div>
          </li>
          <c:if test="${not empty board.realImagePath }">
	        <li><img alt="noImage" src="/uploads/${board.realImagePath }"></li>
          </c:if>
          <li>
            <label for="image">Change Image</label>
            <input type="file" name="image" />
          </li>
          <li><div id="content-caption">Write your content below</div></li>
          <li>
            <input type="hidden" name="content" id="hidden-content" />
            <div
              contenteditable="true"
              id="content"
            ></div>
          </li>
        </ul>

        <div id="bottom-button-bar">
          <button type="submit" id="submit">Write</button>
          <button type="button" id="cancel" onclick="history.back();">Cancel</button>
        </div>
      </form>
    </section>
  </body>
  <script src="/js/board-create.js"></script>
</html>
