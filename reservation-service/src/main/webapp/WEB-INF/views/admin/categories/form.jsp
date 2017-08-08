<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>카테고리 등록</h1>
      <form class="" action="${url}" method="post">
          <input type="text" name="name">
          <input type="submit" name="submit" value="등록">
      </form>
</body>
<script>
	<c:if test="${ error != null}">
			alert("${ error}");
	</c:if>
</script>
</html>