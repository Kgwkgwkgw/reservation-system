<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="../inc/head.jsp">
	<jsp:param value="카테고리 등록" name="title"/>
</jsp:include>
<body>
	<h1>카테고리 등록</h1>
      <form class="" action="/admin/categories" method="post">
          <input type="text" name="name">
          <input type="submit" name="submit" value="등록">
      </form>
</body>
</html>
